package com.java.hotpotserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.hotpotserver.entity.Schedule;
import com.java.hotpotserver.entity.Tables;
import com.java.hotpotserver.entity.Waitinglist;
import com.java.hotpotserver.mapper.ScheduleMapper;
import com.java.hotpotserver.mapper.TablesMapper;
import com.java.hotpotserver.mapper.WaitinglistMapper;
import com.java.hotpotserver.mapper.WishlistMapper;

import com.java.hotpotserver.service.IWaitinglistService;
import com.java.hotpotserver.vo.WaitingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WaitinglistServiceImpl  extends ServiceImpl<WaitinglistMapper, Waitinglist> implements IWaitinglistService { //等待队列接口

   @Autowired
   WaitinglistMapper waitinglistMapper;

   @Autowired
   TablesMapper tablesMapper;

   @Override  public WaitingInfo waiting(String phonenumber) { //等待信息
        QueryWrapper<Waitinglist> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phonenumber",phonenumber);
        Waitinglist selected=waitinglistMapper.selectOne(queryWrapper);
        List<Waitinglist> list=waitinglistMapper.selectList(null);
        int index=list.indexOf(selected);
        if(index==-1)
            return null;
        else
        {
            return new WaitingInfo(selected.getWaitingid(),index);
        }

    }
    @Override
    public int cancelWaiting(String phonenumber) { //取消等待
        QueryWrapper<Waitinglist> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phonenumber",phonenumber);
        int affectedRows=waitinglistMapper.delete(queryWrapper);
        if (affectedRows > 0) {
           return 200;//获取成功
        } else {
            return 404; //获取失败
        }
   }
   public int waitingSuccess(String phonenumber) //排号成功
   {
       QueryWrapper<Waitinglist> queryWrapper=new QueryWrapper<>();
       queryWrapper.eq("phonenumber",phonenumber);
       Waitinglist selected=waitinglistMapper.selectOne(queryWrapper);
       List<Waitinglist> list=waitinglistMapper.selectList(null);
       int index=list.indexOf(selected);
       if(index==-1)
           return 404; //获取失败
       QueryWrapper<Tables> queryWrapper1=new QueryWrapper<>();
       queryWrapper1.eq("vacancy",0);
       int count=tablesMapper.selectCount(queryWrapper1);
       if(count>index)
           return 200;//获取成功
       else
           return 403;
   }

    @Override
    public WaitingInfo askWaitingId(String phonenumber) { //返回排号
       Waitinglist w=new Waitinglist();
       w.setPhonenumber(phonenumber);
       waitinglistMapper.insert(w);
       return waiting(w.getPhonenumber());
   }
}
