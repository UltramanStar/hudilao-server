package com.java.hotpotserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.hotpotserver.entity.Finishedorder;
import com.java.hotpotserver.vo.*;

import java.time.LocalDate;
import java.util.List;

public interface IFinishedorderService extends IService<Finishedorder> { //历史订单接口
    public IPage<Finishedorder> finishedOrderList(String phonenumber, int pagenum, int pagesize); //分页的历史订单列表
    public int comment(int finishedorderid,int comment); //订单评价

    public int addFinishedorder(int tableid,String phonenumber,int money); //增添完成的订单

    public List<Finishedorder> allFinishedOrderList( ); // 所有已完成的订单列表

    public FinishedorderList detailFinishedOrder(int finishedorderid); //历史订单详情

    public boolean findAnother(LocalDate localDate,String s); //查看顾客七天内是否还有其它消费记录

    public List<MonthSale> findMonthSale(); //月售情况

    public List<WeekSale>  findWeekSale(); //周售情况

    public List<YearSale> findYearSale(); //年售情况

    public List<Triple> hotMap(); //热力图

    public void lowerRank(); //评级
}
