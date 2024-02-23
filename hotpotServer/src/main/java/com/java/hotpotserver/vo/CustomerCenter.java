package com.java.hotpotserver.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerCenter {//顾客个人中心显示信息
      String icon;
      String nickname;
      String phonenumber;
      LocalDate birthday;
      int ranks;
}
