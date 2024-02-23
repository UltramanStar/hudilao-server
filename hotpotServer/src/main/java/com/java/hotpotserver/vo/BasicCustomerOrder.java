package com.java.hotpotserver.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BasicCustomerOrder {//顾客每个历史订单的基本信息（不包括菜品）
    private Integer finishedorderid; 

    private Integer tableid;

    private Integer phonenumber;

    private Integer comment;

    private LocalDateTime time;

    private int money;
}
