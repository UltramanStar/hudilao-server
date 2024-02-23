package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class CookAndOrderId {//订单与被派单厨师的id
    private int orderid;

    private String number;

    public CookAndOrderId(int orderid, String number) {
        this.orderid = orderid;
        this.number = number;
    }
}
