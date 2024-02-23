package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class CookItem {//点餐中的一条菜品名和对应数量
    private String name;
    private Integer quantity;
    public CookItem(String name, Integer quantity)
    {
        this.name=name;
        this.quantity=quantity;
    }

}
