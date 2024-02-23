package com.java.hotpotserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.hotpotserver.entity.Customer;
import com.java.hotpotserver.entity.Finishedorder;
import com.java.hotpotserver.entity.Items;
import com.java.hotpotserver.mapper.CustomerMapper;
import com.java.hotpotserver.mapper.FinishedorderMapper;
import com.java.hotpotserver.mapper.ItemsMapper;
import com.java.hotpotserver.service.IFinishedorderService;
import com.java.hotpotserver.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@Service
public class FinishedorderServiceImpl extends ServiceImpl<FinishedorderMapper, Finishedorder> implements IFinishedorderService { //历史订单接口
   @Autowired
   FinishedorderMapper finishedorderMapper;
   @Autowired
    ItemsMapper itemsMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Override
    public IPage<Finishedorder> finishedOrderList(String phonenumber, int pagenum, int pagesize) { //分页的历史订单列表
        Page<Finishedorder> page = new Page<Finishedorder>(pagenum, pagesize);
        QueryWrapper<Finishedorder> finishedorderQueryWrapper = new QueryWrapper<>();
        finishedorderQueryWrapper.eq("phonenumber", phonenumber);
        IPage<Finishedorder> pageResult = finishedorderMapper.selectPage(page, finishedorderQueryWrapper);
        List<Finishedorder> finishedorders = pageResult.getRecords();
        List customerFinishedOrderList = new ArrayList<CustomerFinishedList>();
        for (Finishedorder order : finishedorders) {
            int foodnumber = 0;
            CustomerFinishedList c1 = new CustomerFinishedList();
            BeanUtils.copyProperties(order, c1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = order.getTime().format(formatter);
            c1.setTime(formattedDateTime);
            QueryWrapper<Items> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("finishedorderid", order.getFinishedorderid());
            List<Items> items = itemsMapper.selectList(queryWrapper3);
            int count = 0;
            for (Items i : items) {
                foodnumber += i.getQuantity();
                if (count < 2) { // 只在计数小于2时执行操作
                    {
                        if (count == 0) {
                            c1.setImg1(i.getImg().replaceAll("[\r\n]", ""));
                        }
                        if (count == 1) {
                            c1.setImg2(i.getImg().replaceAll("[\r\n]", ""));
                        }
                    }
                    count++; // 增加计数
                } else {
                    break; // 如果计数达到3，退出循环
                }
            }
                c1.setFoodnumber(foodnumber);
                customerFinishedOrderList.add(c1);
            }
        pageResult.setRecords(customerFinishedOrderList);
        return pageResult;
    }

    @Override
    public int comment(int finishedorderid, int comment) { //订单评价
        Finishedorder finishedorder=new Finishedorder();
        finishedorder.setFinishedorderid(finishedorderid);
        finishedorder.setComment(comment);
        int r=finishedorderMapper.updateById(finishedorder);
        if(r==0)
            return 404; //获取失败
        else
            return 200; //获取成功

    }

    @Override
    public int addFinishedorder(int tableid, String phonenumber, int money) { //增添完成的订单
        Finishedorder o1=new Finishedorder();
        o1.setPhonenumber(phonenumber);
        o1.setMoney(money);
        o1.setTime(LocalDateTime.now());
        o1.setTableid(tableid);
        o1.setComment(0);
        finishedorderMapper.insert(o1);
        return o1.getFinishedorderid();
    }
    @Override
    public List<Finishedorder> allFinishedOrderList() { // 所有已完成的订单列表
        QueryWrapper<Finishedorder> finishedorderQueryWrapper=new QueryWrapper<>();
        List<Finishedorder> f=finishedorderMapper.selectList(finishedorderQueryWrapper);
        return f;
//        List finishedOrderList=new ArrayList<FinishedorderList>();
//        for(Finishedorder o1:f)
//        {
//            FinishedorderList c1=new FinishedorderList();
//            BeanUtils.copyProperties(o1, c1);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String formattedDateTime = o1.getTime().format(formatter);
//            c1.setTime(formattedDateTime);
//            QueryWrapper<Items> queryWrapper3 = new QueryWrapper<>();
//            queryWrapper3.eq("finishedorderid",o1.getFinishedorderid());
//            List<Items> items=itemsMapper.selectList(queryWrapper3);
//            for(Items i:items) {
//                c1.getItems().add(new CustomerItem(i.getFoodname(), i.getQuantity(),i.getPrice(),i.getImg()));
//            }
//            finishedOrderList.add(c1);
//      }
//        return finishedOrderList;
//
}
    @Override
    public FinishedorderList detailFinishedOrder(int finishedorderid) { //历史订单详情
        Finishedorder f=finishedorderMapper.selectById(finishedorderid);
        if(f==null)
            return null;
        else {
            FinishedorderList finishedorderList = new FinishedorderList();
            BeanUtils.copyProperties(f,finishedorderList);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = f.getTime().format(formatter);
            finishedorderList.setTime(formattedDateTime);
            QueryWrapper<Items> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("finishedorderid",finishedorderList.getFinishedorderid());
            List<Items> items=itemsMapper.selectList(queryWrapper3);
            int foodnumber=0;
            for(Items i:items){
                foodnumber+=i.getQuantity();
                finishedorderList.getItems().add(new CustomerItem(i.getFoodname(), i.getQuantity(),i.getPrice(),i.getImg().replaceAll("[\r\n]", "")));
            }
            finishedorderList.setFoodnumber(foodnumber);
            return finishedorderList;
        }
    }
    @Override
    public boolean findAnother(LocalDate localDate, String s) { //查看顾客七天内是否还有其它消费记录
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phonenumber",s);
        List<Finishedorder> list=finishedorderMapper.selectList(queryWrapper);
        LocalDate sevenDaysAgo = localDate.minusDays(7);
        for(Finishedorder f:list) {
            LocalDate otherDate = f.getTime().toLocalDate();
            if (otherDate.isAfter(sevenDaysAgo) && f.getMoney() > 50) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MonthSale> findMonthSale() {
        return finishedorderMapper.monthSale();
    } //月售情况

    @Override
    public List<WeekSale> findWeekSale() {
        return finishedorderMapper.weekSale();
    } //周售情况

    @Override
    public List<YearSale> findYearSale() {
        return finishedorderMapper.yearSale();
    } //年售情况

    @Override
    public List<Triple> hotMap() {
         return finishedorderMapper.hotMap();
    } //热力图

    @Override
    public void lowerRank() { //评级
     List<String> s=finishedorderMapper.getColdCustomer();
     List<Customer> customers=customerMapper.selectList(null);
     for(Customer c: customers)
     {
         boolean isContained = s.contains(c.getPhonenumber());
         if(isContained==false&&c.getRanks()>1)
         {
             int temp=c.getRanks();
             temp--;
             c.setRanks(temp);
             customerMapper.updateById(c);
             }

         }
     }


}
