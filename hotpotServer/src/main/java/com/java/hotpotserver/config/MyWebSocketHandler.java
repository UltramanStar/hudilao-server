package com.java.hotpotserver.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
//websocket的配置文件
public class MyWebSocketHandler extends TextWebSocketHandler {

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 在建立连接时执行操作
    }
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 处理接收到的文本消息
    }
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //
    }
}
