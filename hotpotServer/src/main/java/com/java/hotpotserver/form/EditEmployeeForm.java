package com.java.hotpotserver.form;

import lombok.Data;

@Data
public class EditEmployeeForm {//管理员编辑员工信息时提交的表单
    //要修改的员工id
    int id;
    //工种
    int type;
    //密码
    String password;
    //联系方式
    String phonenumber;
}
