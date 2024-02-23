package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class FoodRanking {//菜品的心愿值总分
    private String food;
    private int totalRankedScore;//根据顾客等级、心愿单中出现次数计算出的某一菜品的心愿值
}
