package com.java.hotpotserver.service;

public interface IItemsService { //顾客的每一条点餐记录

    int addItems(int orderid,int foodid,int quantity,int price); //添加一条点餐记录

    int deleteItems(int orderid); //删除一条点餐记录

    int addFinishedOrderId(int finishedorderid,int orderid); //将本点餐记录添加到历史订单

}
