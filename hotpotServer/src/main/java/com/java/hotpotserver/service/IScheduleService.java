package com.java.hotpotserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.hotpotserver.entity.Schedule;

import java.util.List;

public interface IScheduleService extends IService<Schedule> { //员工排班表

    List<Schedule> getSchedule(); //返回排班表
}
