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
@TableName("schedule")
public class Schedule implements Serializable {//排班表

    private static final long serialVersionUID = 1L;
    //这条排班的月份
    private Integer month;
    //日
    private Integer date;
    //时段（早中晚）
    private Integer time;
    //id
    @TableId(type = IdType.AUTO)
    private Integer id;


}
