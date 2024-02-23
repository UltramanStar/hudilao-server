package com.java.hotpotserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.hotpotserver.entity.Orders;
import com.java.hotpotserver.entity.Schedule;
import com.java.hotpotserver.mapper.OrdersMapper;
import com.java.hotpotserver.mapper.ScheduleMapper;
import com.java.hotpotserver.service.IOrdersService;
import com.java.hotpotserver.service.IScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements IScheduleService { //员工排班表
    @Autowired
    ScheduleMapper scheduleMapper;
    @Override
    public List<Schedule> getSchedule() {
        return scheduleMapper.selectList(null);
    } //返回排班表
}
