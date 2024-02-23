package com.java.hotpotserver.form;

import com.java.hotpotserver.vo.SubmitItem;
import lombok.Data;

import java.util.List;

@Data
public class EditOrderForm {//服务员编辑订单时提交的订单信息表单
    //编辑的订单号
    private int orderid;
    //总价
    private Integer totalprice;
    //菜品列表
    private List<SubmitItem> items;
}
