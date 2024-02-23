package com.java.hotpotserver.form;

import com.java.hotpotserver.vo.SubmitItem;
import lombok.Data;

import java.util.List;

@Data
public class SubmitOrderForm {//顾客和服务员提交订单时提交的表单
    //订单的桌号
    private Integer tableid;
    //总价
    private Integer totalprice;
    //菜品列表
    private List<SubmitItem> items;
}
