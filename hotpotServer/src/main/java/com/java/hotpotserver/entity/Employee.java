package com.java.hotpotserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("employee")
public class Employee implements Serializable {//员工表

    private static final long serialVersionUID = 1L;
    //员工id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //工号
    private String number;
    //工种
    private Integer type;
    //密码
    private String password;
    //入职时间
    private LocalDate registertime;
    //性别
    private Integer sex;
    //电话
    private String phonenumber;
    //姓名
    private String name;
}
