package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class SubmitItem {//提交订单的计算一个菜品的总价格
    private int foodid;
    private int quantity;
    private int price;//数量×单价
}
