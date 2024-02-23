package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class CustomerRank {//传给管理员的计算每个等级会员数量
  int ranks;
  int countPerRank;

}
