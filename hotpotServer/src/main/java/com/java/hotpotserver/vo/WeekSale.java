package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class WeekSale {//周营业额图中的每天营业额数据
    String dayOfWeek;
    int totalMoney;
}
