package com.java.hotpotserver.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class FinishedorderList {//传给管理员的历史订单信息
    private Integer finishedorderid;

    private Integer tableid;

    private Integer phonenumber;

    private Integer comment;

    private String time;

    private int money;

    private int foodnumber;

    List<CustomerItem> items;

    public FinishedorderList()
    {
        items= new ArrayList<>();
    }
}
