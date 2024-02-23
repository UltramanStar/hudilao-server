package com.java.hotpotserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.hotpotserver.entity.Orders;
import com.java.hotpotserver.vo.CookAndOrderId;
import com.java.hotpotserver.vo.CurrentorderList;
import com.java.hotpotserver.vo.WaiterOrderDetail;

import java.util.List;

public interface IOrdersService  extends IService<Orders> { //订单接口
        int orderReady(int orderid); //订单制作好

        List<CurrentorderList> currentOrderList(String phonenumber); //顾客的当前订单
        int orderPaid(int tableid);//根据桌号，支付订单

        IPage<Orders> allPagedCurrentOrderList(int pagenumber,int pagesize); //服务员（分页）

        List<CurrentorderList> allCurrentOrderList();  //服务员（不分页）

       int changeOrderCondition(int orderid,int condition); //改变订单状态

       int urgeOrder(int orderid); //紧急订单

       CookAndOrderId addOrder(int tableid, int totalprice);//添加菜单

       Integer findSuitableCook(List<Integer> list); //分配合适的后厨

       int editOrderPrice(int orderid,int price); //编辑订单价格

       IPage<Orders> pagedLittleOrder(int pagenum,int pagesize); //服务员小订单

       WaiterOrderDetail detailOrderByWaiter(int orderid); //服务员端订单详情

       int findCookIdByOrderId(int orderid); //通过订单号查询后厨id

}
