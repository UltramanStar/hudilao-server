package com.java.hotpotserver.vo;

import lombok.Data;

@Data
public class WebSocketResponse{//websocket发送的信息
    private int type;//表示提示的信息类型
    private String number;//表示提示的接收人
    private String data;//表示提示的具体内容

    public WebSocketResponse(int type, String number, String data) {
        this.type = type;
        this.number = number;
        this.data = data;
    }
}
