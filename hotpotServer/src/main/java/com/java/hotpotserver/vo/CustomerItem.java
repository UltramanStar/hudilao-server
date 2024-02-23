package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class CustomerItem {//顾客点开订单详情后的一条菜品的带图片信息
    private String name;
    private Integer quantity;

    private Integer price;

    private String img;
    public CustomerItem(String name, Integer quantity, Integer price, String img) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.img = img;
    }
}
