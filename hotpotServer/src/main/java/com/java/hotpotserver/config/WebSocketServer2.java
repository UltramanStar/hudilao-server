package com.java.hotpotserver.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket2")

@Component
@Slf4j
public class WebSocketServer2 {
    //private final static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer2> webSocketSet = new CopyOnWriteArraySet<WebSocketServer2>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {  //有连接加入时的操作
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();
        log.info("服务员有新连接加入！当前在线人数为" + getOnlineCount());
//        try {
//           // WebSocketServer.sendInfo("hello");
//        } catch (IOException e) {
//            log.info("websocket IO异常");
//        }
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {   //连接关闭时的操作
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        log.info("服务员有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);

        //群发消息
        for (WebSocketServer2 item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) { //错误处理机制
        log.error("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {  //发送消息机制
        this.session.getBasicRemote().sendText(message);
    }
    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        log.info(message);
        for (WebSocketServer2 item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }  //统计连接的数量

    public static synchronized void addOnlineCount() {
        WebSocketServer2.onlineCount++;
    } //增加连接

    public static synchronized void subOnlineCount() {
        WebSocketServer2.onlineCount--;
    } //减少连接
}
