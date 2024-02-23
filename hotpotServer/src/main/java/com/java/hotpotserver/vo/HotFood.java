package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class HotFood {//带菜品类别的受欢迎菜品名称和销量
    int foodtype;

    String name;

    int monthsale;
}
