package com.java.hotpotserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("announcement")
public class Announcement implements Serializable {//公告表

    private static final long serialVersionUID = 1L;

    @TableId(value = "announcementid", type = IdType.AUTO)
    //公告id
    private Integer announcementid;
    //公告内容
    private String content;
    //发布时间
    private LocalDateTime time;
    //发布对象（服务员、后厨、全体）
    private Integer type;
    //标题
    private String title;
    //是否紧急
    private Integer emergency;

}
