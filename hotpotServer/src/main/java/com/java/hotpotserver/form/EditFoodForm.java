package com.java.hotpotserver.form;


import lombok.Data;

@Data
public class EditFoodForm {//管理员编辑菜品信息时提交的表单
    //修改后价格
    private int price;
    //库存
    private int repository;
    //要修改的菜品id
    private int foodid;
}
