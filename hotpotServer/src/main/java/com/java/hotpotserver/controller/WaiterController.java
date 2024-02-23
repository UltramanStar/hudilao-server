package com.java.hotpotserver.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.hotpotserver.config.WebSocketServer;
import com.java.hotpotserver.entity.Announcement;
import com.java.hotpotserver.entity.Orders;
import com.java.hotpotserver.form.EditOrderForm;
import com.java.hotpotserver.form.SubmitOrderForm;
import com.java.hotpotserver.service.*;
import com.java.hotpotserver.service.impl.EmployeeServiceImpl;
import com.java.hotpotserver.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
@Slf4j
public class WaiterController {
    @Autowired
    IEmployeeService IEmployeeService;//员工服务
    @Autowired
    EmployeeServiceImpl employeeService;//员工服务实现
    @Autowired
    ITableService iTableService;//桌号服务
    @Autowired
    IOrdersService iOrdersService;//进行中订单服务
    @Autowired
    IAnnouncementService iAnnouncementService;//公告服务
    @Autowired
    IFoodService iFoodService;//菜品服务
    @Autowired
    IItemsService iItemsService;//点菜单元服务

    @PostMapping("/waiter/login")//服务员登录验证
    public Result waiterLogin(@RequestParam String number, @RequestParam String password) {
        int r = employeeService.employeeLogin(number, password, 1);
        if (r == 200) {
            return Result.success();//请求成功
        } else {
            return Result.fail();//请求失败
        }
    }

    @PostMapping("/waiter/table")//服务员选择桌号
    public Result waiterTable(@RequestParam int tableid) {
        int r = iTableService.useTable(tableid);
        if (r == 404)
            return Result.fail();//请求失败
        else {
            return Result.success();//请求成功
        }
    }
    @GetMapping("/waiter/page/order")//查看进行中订单
    public Result waiterPagedOrder(@RequestParam int pagenum, @RequestParam int pagesize) {
        Object r = iOrdersService.allPagedCurrentOrderList(pagenum, pagesize);
        if (r != null)
            return Result.success(r);//请求成功
        else
            return Result.fail();//请求失败
    }
    @GetMapping("/waiter/page/littleorder")//查看订单缩略信息
    public Result waiterPageLittleOrder(@RequestParam int pagenum, @RequestParam int pagesize)
    {
        Object r = iOrdersService.pagedLittleOrder(pagenum, pagesize);
        if (r != null)
            return Result.success(r);//请求成功
        else
            return Result.fail();//请求失败
    }
    @GetMapping("/waiter/page/orderdetail")//查看订单详细信息
    public Result waiterPageOrderdetail(@RequestParam int orderid)
    {
        WaiterOrderDetail c=iOrdersService.detailOrderByWaiter(orderid);
         if(c==null)
             return Result.fail();//请求失败
         else
             return Result.success(c);//请求成功
    }
    @PostMapping("/waiter/changeCondition")//改变订单状态（已完成）
    public Result waiterChangeCondition(@RequestParam int orderid, @RequestParam int condition) {
        int r = iOrdersService.changeOrderCondition(orderid, condition);
        if (r == 404)
            return Result.fail();//请求失败
        else
            return Result.success();//请求成功
    }
    @PostMapping("/waiter/pay")//将订单设置为完成支付
    public Result waiterPay(@RequestParam int tableid) {
        int r = iOrdersService.orderPaid(tableid);
        if (r == 200)
            return Result.success();//请求成功
        else
            return Result.fail();//请求失败
    }
    @GetMapping("/waiter/order")//获取所有当前订单信息
    public Result waiterOrder() {
        List<CurrentorderList> o = iOrdersService.allCurrentOrderList();
        if (o == null)
            return Result.fail();//请求失败
        else
            return Result.success(o);//请求成功
    }
    @GetMapping("/waiter/announcement")//获取公告
    public Result waiterAnnouncement(@RequestParam int pagenum, @RequestParam int pagesize) {
        IPage<Announcement> r = iAnnouncementService.pagedHistoryAnnouncement(pagenum, pagesize, 1);
        if (r == null)
            return Result.fail();//请求失败
        else
            return Result.success(r);//请求成功
    }
    @PostMapping("/waiter/urge")//向后厨催菜
    public Result waiterUrge(@RequestParam int orderid)
    {
        int r=iOrdersService.urgeOrder(orderid);
        if(r==404)
            return Result.fail();//请求失败
        else
            return Result.success();//请求成功
    }
    @PostMapping("/waiter/changeContent")//改变订单具体菜品数量等内容
    public Result waiterChangeContent(@RequestBody EditOrderForm form) {

        int r1 = iOrdersService.editOrderPrice(form.getOrderid(), form.getTotalprice());
        int r2 = 200;
        int r3=iItemsService.deleteItems(form.getOrderid());
        for (SubmitItem i : form.getItems()) {
            r2 = iItemsService.addItems(form.getOrderid(), i.getFoodid(), i.getQuantity(), i.getPrice());
            iFoodService.insert(i.getFoodid(),i.getQuantity());
        }
        String cookNumber=IEmployeeService.findNumberById(iOrdersService.findCookIdByOrderId(form.getOrderid()));
        ObjectMapper objectMapper = new ObjectMapper();
        WebSocketResponse webSocketR=new WebSocketResponse(1,cookNumber,"订单"+form.getOrderid()+"的内容有修改，请关注");
        try {
            String jsonString = objectMapper.writeValueAsString(webSocketR);
            WebSocketServer.sendInfo(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (r2 == 200 && r1 == 200&&r3==200)
            return Result.success();//请求成功
        else
            return Result.fail();//请求失败
    }

}
