package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class MonthSale {//月营业额的每周营业额计数
    int weekNumber;
    int totalMoney;
}
