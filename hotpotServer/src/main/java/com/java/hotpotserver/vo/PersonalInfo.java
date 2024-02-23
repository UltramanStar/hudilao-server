package com.java.hotpotserver.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PersonalInfo {//顾客的个人信息（包括头像）
    private String nickname;

    private Integer ranks;

    private String icon;

    private int tableid;
}
