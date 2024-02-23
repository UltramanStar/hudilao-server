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
@TableName("food")
public class Food implements Serializable {//菜品表

    private static final long serialVersionUID = 1L;
    //id
    @TableId(value = "foodid", type = IdType.AUTO)
    private Integer foodid;
    //菜品名
    private String name;
    //类别
    @TableField("foodtype")
    private Integer foodtype;
    //库存
    private Integer repository;
    //单价
    private Integer price;
    //月度销量
    @TableField("monthsale")
    private Integer monthsale;
    //图片url
    private String img;
    //是否置顶
    private Integer top;
    //是否上架
    private Integer inuse;

}
