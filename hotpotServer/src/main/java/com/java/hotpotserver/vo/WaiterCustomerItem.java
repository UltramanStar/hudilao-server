package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class WaiterCustomerItem {//服务员段的计算价格的点菜单元
    private String name;
    private Integer quantity;

    private Integer price;

    private String img;

    private Integer foodid;

    public WaiterCustomerItem(String name, Integer quantity, Integer price, String img, Integer foodid) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.img = img;
        this.foodid = foodid;
    }
}
