package com.java.hotpotserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.hotpotserver.config.WebSocketServer;
import com.java.hotpotserver.config.WebSocketServer2;
import com.java.hotpotserver.entity.Announcement;
import com.java.hotpotserver.entity.Orders;
import com.java.hotpotserver.entity.Schedule;
import com.java.hotpotserver.service.IAnnouncementService;
import com.java.hotpotserver.service.IEmployeeService;
import com.java.hotpotserver.service.IOrdersService;
import com.java.hotpotserver.service.IScheduleService;
import com.java.hotpotserver.vo.CookOrderList;
import com.java.hotpotserver.vo.Result;
import com.java.hotpotserver.vo.WebSocketResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
@Slf4j
public class CookController {
    @Autowired
   private IEmployeeService employeeService;//员工服务
    @Autowired
    private IAnnouncementService iAnnouncementService;//公告服务

    @Autowired
    private IOrdersService iOrdersService;//进行中订单服务

    @Autowired
    private IScheduleService iScheduleService;//排班服务

    @PostMapping("/cook/login")//登录验证
    public Result cookLogin(@RequestParam String number, @RequestParam String password)
    {
       int r=employeeService.employeeLogin(number,password,2);
       if(r==200) {
           return Result.success();//请求成功
       }
       else
       {
           return Result.fail();//请求失败
       }
    }
    @GetMapping("/cook/order")//厨师查看要做的订单
    public Result cookOrder(@RequestParam String number)
    {
        List<CookOrderList> listOrder=employeeService.orderList(number);
        if(listOrder.size()>0)
        {
            return new Result(200,"查找成功",listOrder);

        }
        else
        {
            return new Result(404,"该厨师没有订单",null);
        }
    }
    @GetMapping("/cook/history/announcement")//厨师查看公告列表
    public Result cookAnnouncement()
    {
        List<Announcement> list=iAnnouncementService.historyAnnouncement(2);
        if(list.size()>0)
        {
            return new Result(200,"查找成功",list);
        }
        else
        {
            return new Result(404,"该厨师没有订单",null);
        }
    }
    @PostMapping("/cook/ready")//厨师确认上菜
    public Result cookReady(@RequestParam int orderid)
    {
         int r= iOrdersService.orderReady(orderid);
        try {
            //ObjectMapper objectMapper = new ObjectMapper();
            //WebSocketResponse response = new WebSocketResponse(1, null, "订单"+orderid+"已制作好,请及时上菜");
           // String jsonString = objectMapper.writeValueAsString(response);
            WebSocketServer2.sendInfo("订单"+orderid+"做好");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         if(r==200)
         {
             return Result.success();//请求成功
         }
         else
         {
             return Result.fail();//请求失败
         }
    }
    @GetMapping("/cook/schedule")//厨师查看排班信息
    public Result cookSchedule()
    {
        List<Schedule> list=iScheduleService.getSchedule();
        if(list!=null)
        {
            return Result.success(list);//请求成功
        }
        else
        {
            return Result.fail();//请求失败
        }
    }


}
