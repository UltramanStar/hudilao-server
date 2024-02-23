package com.java.hotpotserver.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;
@Data
public class CustomerByAdmin {//管理员获取的顾客信息
    private LocalDate birthday;

    private String nickname;

    private String phonenumber;

    private Integer customerid;

    private Integer ranks;

    private LocalDate registerdate;

    private String food1;

    private String food2;

    private String food3;
}
