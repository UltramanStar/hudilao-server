package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class LittleOrder {//进行中订单的缩略信息
    int orderid;
    int tableid;
    int emergency;
    int paid;
    int conditions;
    int money;
    int cookid;
    String time;

    int foodnumber;
    String food1;
    int quantity1;
    String img1;

    String food2;
    int quantity2;
    String img2;

    String food3;
    int quantity3;
    String img3;

}
