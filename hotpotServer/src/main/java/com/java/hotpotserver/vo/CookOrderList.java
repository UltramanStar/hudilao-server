package com.java.hotpotserver.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class CookOrderList {//发送给后厨的订单信息（包括菜品）
    private Integer orderid;

    private Integer tableid;

    private LocalDateTime time;

    private String phonenumber;

    private Integer cookid;

    private Integer conditions;

    private Integer emergency;

    List<CookItem> items;

    public CookOrderList()
    {
        items= new ArrayList<>();
    }
}
