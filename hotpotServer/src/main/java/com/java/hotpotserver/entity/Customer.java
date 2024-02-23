package com.java.hotpotserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author fengyu
 * @since 2023-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("customer")
public class Customer implements Serializable {//顾客表

    private static final long serialVersionUID = 1L;
    //生日
    private LocalDate birthday;
    //昵称
    @TableField("nickname")
    private String nickname;
    //电话号
    private String phonenumber;
    //编号id
    @TableId(type = IdType.AUTO)
    private Integer customerid;
    //会员等级
    private Integer ranks;
    //头像
    private String icon;
    //注册日期
    private LocalDate registerdate;
}
