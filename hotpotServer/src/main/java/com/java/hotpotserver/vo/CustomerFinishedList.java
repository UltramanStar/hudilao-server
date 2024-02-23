package com.java.hotpotserver.vo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CustomerFinishedList {//顾客历史订单列表中的单一订单基本信息
    private Integer finishedorderid;

    private Integer comment;

    private String time;

    private int money;

    private int foodnumber;

    private String img1;

    private String img2;
}
