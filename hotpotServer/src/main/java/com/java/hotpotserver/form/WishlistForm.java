package com.java.hotpotserver.form;

import lombok.Data;

@Data
public class WishlistForm {//顾客修改心愿单时提交的表单
    //电话、指示顾客账户
    private String phonenumber;
    //心愿单菜品1
    private String food1;
    //心愿单菜品2
    private String food2;
    //心愿单菜品3
    private String food3;
}
