package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class WaitingInfo {//排队的信息（包括号、等待人数）

    Integer waitingid;
    Integer waitingnumber;
    public WaitingInfo(Integer waitingid, Integer waitingnumber) {
        this.waitingid = waitingid;
        this.waitingnumber = waitingnumber;
    }
}
