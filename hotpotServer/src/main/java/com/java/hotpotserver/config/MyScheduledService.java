package com.java.hotpotserver.config;

import com.java.hotpotserver.service.IFinishedorderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
@Service
//用@Scheduled注解配置定时执行，这里配置为每个月1号的凌晨1点执行，如果会员上个月没有订单，要降级
public class MyScheduledService {
    @Autowired
    IFinishedorderService iFinishedorderService;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置定时查询机制
    // 使用@Scheduled注解
    @Scheduled(cron = "0 0 1 1 * ?") //每个月1号的凌晨1点执行
    public void performTask() {
       log.info("定时任务执行时间：" + dateFormat.format(new Date())); //格式化日期
       iFinishedorderService.lowerRank(); //扫面数据库 看看有没有会员需要降级
    }
}
