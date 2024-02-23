package com.java.hotpotserver.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.hotpotserver.entity.Customer;
import com.java.hotpotserver.entity.Tables;
import com.java.hotpotserver.entity.Wishlist;
import com.java.hotpotserver.form.PersonalCenterForm;
import com.java.hotpotserver.mapper.CustomerMapper;
import com.java.hotpotserver.mapper.TablesMapper;
import com.java.hotpotserver.mapper.WishlistMapper;
import com.java.hotpotserver.service.ICustomerService;
import com.java.hotpotserver.vo.CustomerByAdmin;
import com.java.hotpotserver.vo.PersonalInfo;
import com.java.hotpotserver.vo.NewCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {//关于顾客的服务

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    WishlistMapper wishlistMapper;

    @Autowired
    TablesMapper tablesMapper;

    @Override
    public int WhetherHasPersonalInfo(String phonenumber) { //查看有无顾客账号
        QueryWrapper<Customer> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phonenumber",phonenumber);
        Customer customer=customerMapper.selectOne(queryWrapper);
        if(customer==null)
        {
            Customer customer1=new Customer();
            customer1.setPhonenumber(phonenumber);
            customer1.setRanks(1);
            customer1.setRegisterdate(LocalDate.now());
            customer1.setPhonenumber(phonenumber);
            String sub=phonenumber.substring(phonenumber.length()-4,phonenumber.length());
            customer1.setNickname("湖底捞"+sub);
            customer1.setIcon("https://hudilao.oss-cn-nanjing.aliyuncs.com/2023-09-06/e2100e6c-3a9b-4dc8-8431-12798f0f2c73.jpg");
            customerMapper.insert(customer1);
            Wishlist wishlist=new Wishlist();
            wishlist.setPhonenumber(phonenumber);
            wishlistMapper.insert(wishlist);
            return 404; //获取失败
        }
        else
        {
//            PersonalInfo personalInfo=new PersonalInfo();
//            BeanUtils.copyProperties(customer, personalInfo);
//            QueryWrapper<Wishlist> queryWrapper1=new QueryWrapper<>();
//            queryWrapper1.eq("phonenumber",phonenumber);
//            Wishlist wishlist=wishlistMapper.selectOne(queryWrapper1);
//            BeanUtils.copyProperties(wishlist, personalInfo);
//            return personalInfo;
            return 200; //获取成功
        }
    }
    @Override
    public int personalCenterEdit(PersonalCenterForm personalCenterForm) { //编辑个人信息
        QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
        customerQueryWrapper.eq("phonenumber",personalCenterForm.getPhonenumber());
        Customer customer=customerMapper.selectOne(customerQueryWrapper);
        if(customer==null)
            return 404; //获取失败
        customer.setIcon(personalCenterForm.getIcon());
        customer.setBirthday(personalCenterForm.getBirthday());
        customer.setNickname(personalCenterForm.getNickname());
        customerMapper.updateById(customer);
        return 200; //获取成功

    }
    @Override
    public Customer personalCenterInfo(String phonenumber) { //顾客个人中心
        QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
        customerQueryWrapper.eq("phonenumber",phonenumber);
        Customer customer=customerMapper.selectOne(customerQueryWrapper);
        return customer;

    }
    @Override
    public List<CustomerByAdmin> findAllCustomer() { //返回所有顾客
        List<CustomerByAdmin> c=new ArrayList<CustomerByAdmin>();
        //QueryWrapper queryWrapper1=new QueryWrapper<>();
        List<Customer> list=customerMapper.selectList(null);
        for(Customer customer:list) {
            CustomerByAdmin customerByAdmin = new CustomerByAdmin();
            BeanUtils.copyProperties(customer, customerByAdmin);
            QueryWrapper<Wishlist> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phonenumber", customer.getPhonenumber());
            Wishlist w = wishlistMapper.selectOne(queryWrapper);
            BeanUtils.copyProperties(w, customerByAdmin);
            c.add(customerByAdmin);
        }
        return c;
    }
    @Override
    public PersonalInfo PersonalInfo(String phonenumber) { //顾客个人信息
        QueryWrapper<Customer> customerQueryWrapper=new QueryWrapper<>();
        customerQueryWrapper.eq("phonenumber",phonenumber);
        Customer customer=customerMapper.selectOne(customerQueryWrapper);
        PersonalInfo p=new PersonalInfo();
        if(customer==null)
            return null;
        BeanUtils.copyProperties(customer,p);
        QueryWrapper<Tables> tablesQueryWrapper=new QueryWrapper<>();
        tablesQueryWrapper.eq("phonenumber",phonenumber);
        Tables t=tablesMapper.selectOne(tablesQueryWrapper);
        if(t==null)
            p.setTableid(0);
        else
            p.setTableid(t.getTableid());
        return p;
    }

    @Override
    public NewCustomer newC() { //新增顾客
       NewCustomer c=new NewCustomer();
       c.setList1(customerMapper.findThisMonth());
       c.setList2(customerMapper.findLastMonth());
       c.setList3(customerMapper.totalRank());
       return c;

    }

}
