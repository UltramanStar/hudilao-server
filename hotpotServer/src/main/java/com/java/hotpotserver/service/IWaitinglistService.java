package com.java.hotpotserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.hotpotserver.entity.Waitinglist;
import com.java.hotpotserver.vo.WaitingInfo;

public interface IWaitinglistService extends IService<Waitinglist> { //等待队列接口
     WaitingInfo waiting(String phonenumber); //等待信息

     int cancelWaiting(String phonenumber); //取消等待

     public int waitingSuccess(String phonenumber); //排号成功

     WaitingInfo askWaitingId(String phonenumber); //返回排号

}
