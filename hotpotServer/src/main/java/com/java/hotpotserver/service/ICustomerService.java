package com.java.hotpotserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.hotpotserver.entity.Customer;
import com.java.hotpotserver.form.PersonalCenterForm;
import com.java.hotpotserver.vo.CustomerByAdmin;
import com.java.hotpotserver.vo.PersonalInfo;
import com.java.hotpotserver.vo.NewCustomer;

import java.util.List;


public interface ICustomerService extends IService<Customer> {//关于顾客的服务

    int WhetherHasPersonalInfo(String phonenumber);//查看有无顾客账号

    int personalCenterEdit(PersonalCenterForm personalCenterForm);//编辑个人信息

    Customer personalCenterInfo(String phonenumber); //顾客个人中心

    List<CustomerByAdmin> findAllCustomer(); //返回所有顾客

    PersonalInfo PersonalInfo(String phonenumber); //顾客个人信息

    NewCustomer newC(); //新增顾客

}
