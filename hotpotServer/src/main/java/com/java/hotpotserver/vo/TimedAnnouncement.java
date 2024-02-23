package com.java.hotpotserver.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TimedAnnouncement {//公告信息



    private Integer announcementid;

    private String content;

    private String time;

    private Integer type;

    private String title;

    private Integer emergency;
}
