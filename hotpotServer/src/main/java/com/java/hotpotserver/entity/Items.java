package com.java.hotpotserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("items")
public class Items implements Serializable {//点菜单元（表示顾客点的一道菜）

    private static final long serialVersionUID = 1L;

    //id
    @TableId(type = IdType.AUTO)
    private Integer itemid;
    //所属订单id
    private Integer finishedorderid;
    //所属进行中订单id
    @TableField("orderid")
    private Integer orderid;

    //菜品名
    private String foodname;
    //菜品id
    @TableField("foodid")
    private Integer foodid;

    //数量
    private Integer quantity;
    //价格
    private Integer price;
    //图片
    private String img;

}
