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
@TableName("waitinglist")
public class Waitinglist {//排队队列表
    //等待客户id
    @TableId(type = IdType.AUTO)
    private Integer waitingid;
    //电话
    private String phonenumber;

}
