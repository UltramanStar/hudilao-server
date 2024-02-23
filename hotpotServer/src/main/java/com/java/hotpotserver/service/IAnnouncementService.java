package com.java.hotpotserver.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.hotpotserver.entity.Announcement;
import com.java.hotpotserver.form.AnnouncementForm;

import java.util.List;

public interface IAnnouncementService extends IService<Announcement> {  //对公告进行删除、发布、发送给服务员、查看历史公告

    List<Announcement> historyAnnouncement(int type);//历史公告

    int callWaiter(int tableid);//呼叫服务员

    IPage<Announcement> pagedHistoryAnnouncement(int pagenum,int pagesize,int type);//分页历史公告

    List<Announcement> allHistoryAnnouncement();//全部历史公告

    int deleteById(int announcementid); // 删除公告

    int addAnnouncement(AnnouncementForm announcementForm);//添加新公告

}
