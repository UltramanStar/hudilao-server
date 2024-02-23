package com.java.hotpotserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.hotpotserver.config.WebSocketServer;
import com.java.hotpotserver.config.WebSocketServer2;
import com.java.hotpotserver.entity.*;
import com.java.hotpotserver.form.EditOrderForm;
import com.java.hotpotserver.form.PersonalCenterForm;
import com.java.hotpotserver.form.SubmitOrderForm;
import com.java.hotpotserver.form.WishlistForm;
import com.java.hotpotserver.mapper.FinishedorderMapper;
import com.java.hotpotserver.mapper.FoodMapper;
import com.java.hotpotserver.mapper.ItemsMapper;
import com.java.hotpotserver.mapper.OrdersMapper;
import com.java.hotpotserver.service.*;
import com.java.hotpotserver.vo.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import com.mashape.unirest.http.exceptions.UnirestException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("api")
@Slf4j
public class CustomerController {
    @Autowired
    ICustomerService iCustomerService;//顾客服务
    @Autowired
    IWaitinglistService iWaitinglistService;//排队服务

    @Autowired
    ITableService iTableService;//占桌服务

    @Autowired
    IFoodService iFoodService;//菜品服务

    @Autowired
    IAnnouncementService iAnnouncementService;//公告服务

    @Autowired
    IFinishedorderService iFinishedorderService;//已完成订单服务

    @Autowired
    IWishlistService iWishlistService;//心愿单服务

    @Autowired
    IOrdersService iOrdersService;//进行中订单服务

    @Autowired
    IItemsService iItemsService;//点菜单元服务

    @Autowired
    ItemsMapper itemsMapper;//点菜数据访问

    @Autowired
    FinishedorderMapper finishedorderMapper;//已完成订单访问

    @Autowired
    OrdersMapper ordersMapper;//进行中订单访问

    @PostMapping ("/customer/code")//获取验证码
        public Result customerCode(@RequestParam String phonenumber) {
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        String randomString = String.valueOf(randomNumber);
        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest.post("https://dfsns.market.alicloudapi.com/data/send_sms")
                    .header("Authorization", "APPCODE 126ec0ea5f2b4fb7a1f444701d936134")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("content", "code:" + randomString)
                    .field("template_id", "CST_ptdie100")
                    .field("phone_number", phonenumber)
                    .asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        return Result.success(randomString);
    }
    @GetMapping("/customer/login")//顾客登录
    public Result customerLogin(@RequestParam String phonenumber)
    {
        int r=iCustomerService.WhetherHasPersonalInfo(phonenumber);
        if(r==404)
            return new Result(404,"注册成功",null);
        else
            return  new Result(200,"已经注册",null);
    }
      @GetMapping("/customer/personal")//个人基本信息
      public Result customerPersonal(@RequestParam String phonenumber)
      {
          PersonalInfo p=iCustomerService.PersonalInfo(phonenumber);
          if(p==null)
              return Result.fail();//请求失败
          else
              return Result.success(p);//请求成功
      }
      @GetMapping("/customer/wish")//查看心愿单
      public Result customerWish(@RequestParam String phonenumber)
      {
          WishListNoPhone no=iWishlistService.wishListNoPhone(phonenumber);
          if(no==null)
              return Result.fail();//请求失败
          else
              return Result.success(no);//请求成功
      }

   @PostMapping("/customer/ask/waitingid")//排队取号
  public Result customerAskWaitingid(@RequestParam String phonenumber)
   {
       WaitingInfo waitingInfo=iWaitinglistService.askWaitingId(phonenumber);
       if(waitingInfo==null)
           return Result.fail();//请求失败
       else
           return Result.success(waitingInfo);//请求成功
   }
    @GetMapping("/customer/waitingInfo")//取号信息（等待情况）
    public Result customerWaiting(@RequestParam String phonenumber)
    {
        WaitingInfo waitingInfo=iWaitinglistService.waiting(phonenumber);

        if(waitingInfo!=null)
        {
            return Result.success(waitingInfo);//请求成功
        }
        else
        {
            return Result.fail();//请求失败
        }
    }
    @DeleteMapping("/customer/cancel")//取消就餐
    public Result customerCancel(@RequestParam String phonenumber)
    {
        int r=iWaitinglistService.cancelWaiting(phonenumber);
        if(r==200)
            return Result.success();//请求成功
        else
            return Result.fail();//请求失败
    }
    @PostMapping("/customer/confirm")//确认就餐
    public Result customerConfirm(@RequestParam String phonenumber)
    {
        int r=iTableService.addCustomer(phonenumber);
        int r2=iWaitinglistService.cancelWaiting(phonenumber);
        if(r==404|r2==404)
            return Result.fail();//请求失败
        else {
            Map<String, Integer> jsonMap = new HashMap<>();
            jsonMap.put("tableid",r);
            return Result.success(jsonMap);//请求成功
        }
    }
    @GetMapping("/customer/foodList")//获取菜品列表
    public Result foodList()
    {
        List<Food> foodList=iFoodService.foodList();
        if(foodList!=null)
            return Result.success(foodList);//请求成功
        else
        {
            return Result.fail();//请求失败
        }
    }
    @PostMapping("/customer/call")//呼叫服务员
    public Result customerCall(@RequestParam int tableid)
    {
        int r=iAnnouncementService.callWaiter(tableid);
        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            WebSocketResponse response = new WebSocketResponse(1, null, "桌"+tableid+"呼叫服务员");
//            String jsonString = objectMapper.writeValueAsString(response);
            WebSocketServer2.sendInfo("桌"+tableid+"呼叫");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(r==200)
            return Result.success();//请求成功
        else
            return Result.fail();//请求失败
    }
    @PostMapping("/customer/edit/center")//编辑个人中心信息
    public Result customerCenter(@RequestBody PersonalCenterForm form)
    {
        int r=iCustomerService.personalCenterEdit(form);
        if(r==200)
            return Result.success();//请求成功
        else
           return Result.fail();//请求失败
    }
    @GetMapping("/customer/finishedOrder")//查看历史订单
    public Result customerFinishedOrder(@RequestParam String phonenumber,@RequestParam int pagenum,@RequestParam int pagesize)
    {
       Object r=iFinishedorderService.finishedOrderList(phonenumber,pagenum,pagesize);
       if(r!=null)
           return Result.success(r);//请求成功
       else
           return Result.fail();//请求失败
    }
    @GetMapping("/customer/orderdetail")//查看历史订单详细信息
    public Result customerOrderdetail(int finishedorderid)
    {
        FinishedorderList f=iFinishedorderService.detailFinishedOrder(finishedorderid);
        if(f==null)
            return Result.fail();//请求失败
        else
            return Result.success(f);//请求成功
    }
    @GetMapping("/customer/center")//进入个人中心查看信息
    public Result customerCenter(@RequestParam String phonenumber)
    {
        Customer r=iCustomerService.personalCenterInfo(phonenumber);
        if(r==null)
            return Result.fail();//请求失败
        else {  CustomerCenter c=new CustomerCenter();
               BeanUtils.copyProperties(r,c);
                return Result.success(c);//请求成功
        }
    }
    @PostMapping("/customer/submitWishList")//提交心愿单
    public Result customerSubmitWishList(@RequestBody WishlistForm wishlistForm)
    {
        int r=iWishlistService.editWishlist(wishlistForm);
        if(r==200)
            return Result.success();//请求成功
        else
            return Result.fail();//请求失败

    }
    @PostMapping("/customer/comment")//评价历史订单（打星）
    public Result customerComment(@RequestParam int finishedorderid, @RequestParam int comment)
    {
        int r=iFinishedorderService.comment(finishedorderid,comment);
        if(r==200)
            return Result.success();//请求成功
        else
            return Result.fail();//请求失败
    }
    @GetMapping("customer/current/order")//查看当前订单情况
    public Result customerCurrentOrder(@RequestParam String phonenumber)
    {
        Object r=iOrdersService.currentOrderList(phonenumber);
        if(r!=null)
            return Result.success(r);//请求成功
        else
            return Result.fail();//请求失败
    }
    @PostMapping("/customer/pay")//支付当前订单
    public Result customerPay(@RequestParam int tableid)
    {
       int r=iOrdersService.orderPaid(tableid);
       if(r==200)
         return Result.success( );//请求成功
       else
           return Result.fail();//请求失败
    }
    @GetMapping("/customer/success")//排队成功
    public Result cusotomerSuccess(@RequestParam String phonenumber) {
        int r = iWaitinglistService.waitingSuccess(phonenumber);
        return new Result(r, null, null);
    }
    @PostMapping("/customer/submitOrder")//提交订单
    @ResponseBody
    public Result customerSubmitOrder(@RequestBody SubmitOrderForm form)
    {
        CookAndOrderId r1=iOrdersService.addOrder(form.getTableid(),form.getTotalprice());
        int r2=200;
        for(SubmitItem i:form.getItems())
        {
           r2=iItemsService.addItems(r1.getOrderid(),i.getFoodid(),i.getQuantity(),i.getPrice());
           iFoodService.insert(i.getFoodid(),i.getQuantity());
        }
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            WebSocketResponse r=new WebSocketResponse(2,r1.getNumber(),"有新增订单，订单号为"+r1.getOrderid());
            String jsonString = objectMapper.writeValueAsString(r);
            WebSocketServer.sendInfo(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(r2==200)
            return Result.success();//请求成功
        else
            return Result.fail();//请求失败
    }


}
