package com.java.hotpotserver.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data

public class PersonalCenterForm {//顾客修改个人信息时提交的表单
    //昵称
    String nickname;
    //头像
    String icon;
    //电话
    String phonenumber;
    //生日
    LocalDate birthday;
}
