package com.java.hotpotserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tables")
public class Tables {//桌号表，表示每一桌占用情况
    //id
    @TableId(type = IdType.AUTO)
    //桌号
    private Integer tableid;
    //顾客电话
    private String phonenumber;
    //是否可用（1占用）
    private Integer vacancy;
}
