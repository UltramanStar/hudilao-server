package com.java.hotpotserver.vo;


import lombok.Data;

@Data
public class ColdFood {//管理员请求的紧缺菜品信息
    int foodtype;

    String name;

    int repository;
}
