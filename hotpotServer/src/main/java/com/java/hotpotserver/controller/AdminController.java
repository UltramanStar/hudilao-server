package com.java.hotpotserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.hotpotserver.config.WebSocketServer;
import com.java.hotpotserver.entity.Announcement;
import com.java.hotpotserver.entity.Employee;
import com.java.hotpotserver.entity.Finishedorder;
import com.java.hotpotserver.entity.Food;
import com.java.hotpotserver.form.*;
import com.java.hotpotserver.mapper.WishlistMapper;
import com.java.hotpotserver.service.*;
import com.java.hotpotserver.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("api")
@Slf4j
public class AdminController {  //管理员控制器

     @Autowired
    IFinishedorderService iFinishedorderService; //已完成订单服务

     @Autowired
    IAnnouncementService iAnnouncementService;  //公告服务

     @Autowired
    ICustomerService iCustomerService;  //顾客服务

     @Autowired
    IEmployeeService iEmployeeService;  //员工服务

     @Autowired
     IFoodService foodService; //菜品服务

     @Autowired
    WishlistMapper wishlistMapper; //心愿单的Mapper

    @GetMapping("/admin/finishedOrder") //
    public Result adminFinishedOrder()
    {
        List<Finishedorder> f1=iFinishedorderService.allFinishedOrderList();
        if(f1==null)
            return Result.fail();//请求失败
        else
            return Result.success(f1);//请求成功
    }
    @GetMapping("/admin/finishedOrder/detail")
    public Result adminFinishedOrderDetail(@RequestParam int finishedorderid) //已完成订单的细节信息，包括菜品的具体数量
    {
        FinishedorderList f=iFinishedorderService.detailFinishedOrder(finishedorderid);
        if(f==null)
            return Result.fail();//请求失败
        else
            return Result.success(f);//请求成功
    }
    @GetMapping("/admin/announcement") //管理员添加新的订单
    public Result adminAnnouncement()
    {
       List<Announcement> list=iAnnouncementService.allHistoryAnnouncement();
       if(list==null)
           return Result.fail();//请求失败
       else
           return Result.success(list);//请求成功
    }

    @GetMapping("/admin/customer")
    public Result adminCustomer( ) //管理员查看所有的顾客
    {
        log.info("hello");
        List<CustomerByAdmin> customerByAdmins=iCustomerService.findAllCustomer();
        if(customerByAdmins==null)
            return Result.fail();//请求失败
        else
            return Result.success(customerByAdmins);//请求成功
    }
    @GetMapping("/admin/employee") //管理员查看所有的员工
    public Result adminEmployee()
    {
       List<Employee> l=iEmployeeService.listEmployee();
       if(l==null)
           return Result.fail();//请求失败
       else
           return Result.success(l);//请求成功
    }
    @PostMapping("/admin/addEmployee") //添加新的员工
    public Result adminAddEmployee(@RequestBody AddEmployeeForm add)
    {
       int r=iEmployeeService.addEmployee(add);
       if(r==404)
           return Result.fail();//请求失败
       else
           return Result.success();//请求成功
    }
    @PostMapping("/admin/editEmployee")//管理员编辑员工信息
    public Result EditEmployee(@RequestBody EditEmployeeForm edit)
    {
         int r=iEmployeeService.editEmployee(edit);
         if(r==404)
             return Result.fail();//请求失败
         else
             return Result.success();//请求成功
    }
    @DeleteMapping("/admin/deleteEmployee")//管理员删除员工
    public Result adminDeleteEmployee(@RequestParam int id)
    {
       int r=iEmployeeService.deleteEmployee(id);
        if(r==404)
            return Result.fail();//请求失败
        else
            return Result.success();//请求成功
    }
    @GetMapping("/admin/food")//管理员查看菜品
    public Result adminFood()
    {
        List<Food> foodList=foodService.adminFoodList();
        for(Food f:foodList)
        {
            String originalString = f.getImg();
            String cleanedString = originalString.replaceAll("[\r\n]", "");
            f.setImg(cleanedString);
        }
        if(foodList==null)
            return Result.fail();//请求失败
          else
             return Result.success(foodList);//请求成功
    }
    @DeleteMapping("/admin/deleteAnnouncement")//管理员删除公告
    public Result adminDeleteAnnouncement(@RequestParam  int announcementid)
    {
          int r=iAnnouncementService.deleteById(announcementid);
        if(r==404)
            return Result.fail();//请求失败
        else
            return Result.success();//请求成功
    }
    @PostMapping("/admin/food/top")//管理员置顶菜品
    public  Result adminFoodTop(@RequestParam int foodid)
    {
        int r=foodService.setTop(foodid,1);
        if(r==404)
            return Result.fail();//请求失败
        else
            return Result.success();//请求成功
    }
    @PostMapping("/admin/food/untop")//管理员取消置顶菜品
    public  Result adminFoodUnTop(@RequestParam int foodid)
    {
        int r=foodService.setTop(foodid,0);
        if(r==404)
            return Result.fail();//请求失败
        else
            return Result.success();//请求成功
    }
    @PostMapping("/admin/food/add")//管理员添加菜品
    public Result adminFoodAdd(@RequestBody AddFoodForm add)
    {
       int r=foodService.addFood(add);
        if(r==404)
            return Result.fail();//请求失败
        else
            return Result.success();//请求成功
    }
    @PostMapping("/admin/food/edit")//管理员编辑菜品
    public Result adminFoodAdd(@RequestBody EditFoodForm edit)
    {
       int r=foodService.editFood(edit);
        if(r==404)
            return Result.fail();//请求失败
        else
            return Result.success();//请求成功
    }
    @PostMapping("/admin/food/unuse")//管理员下架菜品
    public  Result adminFoodUnUse(@RequestParam int foodid)
    {
        int r=foodService.setUse(foodid,1);
        if(r==404)
            return Result.fail();//请求失败
        else
            return Result.success();//请求成功
    }
    @PostMapping("/admin/food/use")//管理员上架菜品
    public  Result adminFoodUse(@RequestParam int foodid)
    {
        int r=foodService.setUse(foodid,0);
        if(r==404)
            return Result.fail();//请求失败
        else
            return Result.success();//请求成功
    }
   @PostMapping("/admin/announcement/add")//管理员发布公告
    public Result adminAddAnn(@RequestBody AnnouncementForm announcementForm)
   {
       int r=iAnnouncementService.addAnnouncement(announcementForm);
       if(announcementForm.getType()==2||announcementForm.getType()==3) {
           try {
               ObjectMapper objectMapper = new ObjectMapper();
               WebSocketResponse response = new WebSocketResponse(3, "公告", "有新的公告："+announcementForm.getTitle());
               String jsonString = objectMapper.writeValueAsString(response);
               WebSocketServer.sendInfo(jsonString);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }
       if(r==404)
           return Result.fail();//请求失败
       else
           return Result.success();//请求成功
   }
   @GetMapping("/admin/top/wishList")//管理员查看心愿值榜单
    public Result adminTopWishList()
   {
      List<FoodRanking> f=wishlistMapper.findTop5FoodRankings();
      if(f==null)
          return Result.fail();//请求失败
      else
          return Result.success(f);//请求成功

   }
   @GetMapping("/admin/sale/week")//查看周营业额
    public Result adminSaleWeek() {
       List<WeekSale> a = iFinishedorderService.findWeekSale();
       if (a == null)
           return Result.fail();//请求失败
       else
           return Result.success(a);//请求成功
   }
   @GetMapping("/admin/sale/month")//查看月营业额
   public Result adminSaleMonth() {

       List<MonthSale> a =iFinishedorderService.findMonthSale();
       if (a == null)
           return Result.fail();//请求失败
       else
           return Result.success(a);//请求成功
    }
    @GetMapping("/admin/sale/year")//查看年营业额
    public Result adminSaleYear() {

        List<YearSale> a =iFinishedorderService.findYearSale();
        if (a == null)
            return Result.fail();//请求失败
        else
            return Result.success(a);//请求成功
    }
    @GetMapping("/admin/vip")//查看本月上月新增会员数和会员总数占比数据
    public Result adminVip()
    {
        NewCustomer n=iCustomerService.newC();
        if (n == null)
            return Result.fail();//请求失败
        else
            return Result.success(n);//请求成功
    }
    @GetMapping("/admin/hot")//查看各品类热销菜品前五
    public Result adminHot()
    {
        List<HotFood> f=foodService.hotFood();
        if (f == null)
            return Result.fail();//请求失败
        else
            return Result.success(f);//请求成功
    }
    @GetMapping("/admin/urgent")//查看紧缺菜品
    public Result adminCold()
    {
        List<ColdFood> f=foodService.coldFood();
        if (f == null)
            return Result.fail();//请求失败
        else
            return Result.success(f);//请求成功
    }
    @GetMapping("/admin/hotMap")//查看一周内订单热点图
    public Result adminHotMap()
    {
        List<Triple> f=iFinishedorderService.hotMap();
        if (f == null)
            return Result.fail();//请求失败
        else
            return Result.success(f);//请求成功
    }

}
