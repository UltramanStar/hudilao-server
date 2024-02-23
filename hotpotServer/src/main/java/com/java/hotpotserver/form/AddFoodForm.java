package com.java.hotpotserver.form;

import lombok.Data;

@Data
public class AddFoodForm {//管理员添加菜品时提交的表单
    //菜品名称
    private String name;
    //菜品类别
    private int foodtype;
    //菜品单价
    private int price;
    //库存
    private int repository;
    //图片url
    private String img;
}
