package com.java.hotpotserver.vo;


import lombok.Data;

@Data
public class Triple {//管理员请求的一周订单热点图渲染所需的三元组
    int dayOfWeek;
    int orderHour;
    int orderCount;
}
