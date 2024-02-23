package com.java.hotpotserver.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class CurrentorderList {//顾客的当前订单信息（包括菜品）

    private Integer orderid;

    private Integer tableid;

    private Integer money;

    private String time;

    private String phonenumber;

    private Integer cookid;

    private Integer conditions;

    private Integer emergency;

    private Integer paid;

    List<CustomerItem> items;

    int foodnumber;
    public CurrentorderList()
    {
        items= new ArrayList<>();
    }
}
