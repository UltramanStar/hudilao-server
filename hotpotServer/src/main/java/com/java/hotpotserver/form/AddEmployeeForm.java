package com.java.hotpotserver.form;

import lombok.Data;

@Data
public class AddEmployeeForm {//管理员添加员工时提交的表单

    private String name;//员工姓名

    private String phonenumber;//员工联系方式

    private String number;//员工工号

    private Integer type;//员工工种

    private String password;//员工登录的密码

    Integer sex;//性别
}
