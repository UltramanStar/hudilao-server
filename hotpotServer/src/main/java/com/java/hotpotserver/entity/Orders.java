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
@TableName("orders")
public class Orders implements Serializable {//进行中订单表

    private static final long serialVersionUID = 1L;
    //id
    @TableId(value = "orderid", type = IdType.AUTO)
    private Integer orderid;
    //桌号
    private Integer tableid;
    //时间
    private LocalDateTime time;
    //顾客电话号
    private String phonenumber;
    //派给的初始号
    private Integer cookid;
    //订单状态（已下单、已上菜）
    private Integer conditions;
    //是否紧急
    private Integer emergency;
    //是否支付
    private Integer paid;
    //总价格
    private Integer money;
}
