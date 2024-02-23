package com.java.hotpotserver.form;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AnnouncementForm {//管理员发布公告时提交的表单
    //公告标题
    String title;
    //内容
    String content;
    //发送对象
    Integer type;
    //是否紧急
    Integer emergency;

}
