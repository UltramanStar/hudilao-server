package com.java.hotpotserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.hotpotserver.config.WebSocketServer;
import com.java.hotpotserver.entity.Announcement;
import com.java.hotpotserver.entity.Finishedorder;
import com.java.hotpotserver.entity.Items;
import com.java.hotpotserver.entity.Orders;
import com.java.hotpotserver.form.AnnouncementForm;
import com.java.hotpotserver.mapper.AnnouncementMapper;
import com.java.hotpotserver.service.IAnnouncementService;
import com.java.hotpotserver.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
@Slf4j
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements IAnnouncementService { //对公告进行删除、发布、发送给服务员、查看历史公告
    @Autowired
    AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> historyAnnouncement(int type) {  //历史公告
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type).or().eq("type",3);
        queryWrapper.orderByDesc("time");
        List<Announcement> announcement = announcementMapper.selectList(queryWrapper);
        return announcement;
    }
    @Override
    public int callWaiter(int tableid) { //呼叫服务员
        Announcement a=new Announcement();
        a.setTime(LocalDateTime.now());
        a.setType(1);
        a.setTitle("呼叫");
        a.setContent("桌"+tableid+"在"+LocalDateTime.now()+"呼叫服务员"); //websocket提示
        a.setEmergency(1);
        int row=announcementMapper.insert(a);
        if(row==0)
            return 404; //获取失败
        else
        {
            return 200; //获取成功
        }

    }

    @Override
    public IPage<Announcement> pagedHistoryAnnouncement(int pagenum, int pagesize, int type) { //分页历史公告
        Page<Announcement> page = new Page<Announcement>(pagenum, pagesize);
        QueryWrapper<Announcement> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",type).or().eq("type",3);
        queryWrapper.orderByDesc("time");
        IPage<Announcement> pageResult =announcementMapper.selectPage(page,queryWrapper);
        List<Announcement> currentList=pageResult.getRecords();
        List timeList=new ArrayList<TimedAnnouncement>();
        for(Announcement a: currentList)
        {
            TimedAnnouncement c1=new TimedAnnouncement();
            BeanUtils.copyProperties(a, c1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = a.getTime().format(formatter);
            c1.setTime(formattedDateTime);
            timeList.add(c1);
        }
        pageResult.setRecords(timeList);
        return pageResult;
    }

    @Override
    public List<Announcement> allHistoryAnnouncement() { //全部历史公告
            QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type", 1).or().eq("type",2).or().eq("type",3);
            queryWrapper.orderByDesc("time");
            List<Announcement> announcement = announcementMapper.selectList(queryWrapper);
            return announcement;
    }

    @Override
    public int deleteById(int announcementid) { // 删除公告
        int r=announcementMapper.deleteById(announcementid);
        if(r==0)
            return 404; //获取失败
        else
            return 200; //获取成功
    }

    @Override
    public int addAnnouncement(AnnouncementForm announcementForm) { //添加新公告
        Announcement a=new Announcement();
        BeanUtils.copyProperties(announcementForm,a);
        a.setTime(LocalDateTime.now());
        int r=announcementMapper.insert(a);
        if(r>0)
            return 200; //获取成功
        else
            return 404; //获取失败
    }

}
