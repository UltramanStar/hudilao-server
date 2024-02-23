package com.java.hotpotserver.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class WaiterOrderDetail {//服务员查看的订单详细信息
    private Integer orderid;

    private Integer tableid;

    private Integer money;

    private String time;

    private String phonenumber;

    private Integer cookid;

    private Integer conditions;

    private Integer emergency;

    private Integer paid;

    List<WaiterCustomerItem> items;

    int foodnumber;
    public WaiterOrderDetail()
    {
        items= new ArrayList<>();
    }
}
