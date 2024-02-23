package com.java.hotpotserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.hotpotserver.entity.Tables;

public interface ITableService extends IService<Tables> { //餐桌操作接口
     int addCustomer(String phonenumber); //为每桌添加顾客

     int useTable(int tableid); //使用该桌
     int clearTable(int tableid); //清空该桌

      String findPhonenumberByTableId(int tableid); //通过桌号查询顾客手机号

}
