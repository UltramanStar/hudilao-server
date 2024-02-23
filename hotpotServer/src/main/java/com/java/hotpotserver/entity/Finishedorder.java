package com.java.hotpotserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("finishedorder")
public class Finishedorder implements Serializable {//已完成订单表

    private static final long serialVersionUID = 1L;
    //订单id
    @TableId(value = "finishedorderid", type = IdType.AUTO)
    private Integer finishedorderid;
    //桌号
    private Integer tableid;
    //顾客电话
    private String phonenumber;
    //评价
    private Integer comment;
    //订单时间
    private LocalDateTime time;
    //总价格
    private int money;

}
