package com.yjh.web.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yjh.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yujunhong
 * @date 2021/5/18 13:41
 */
@Component
@ServerEndpoint(value = "/server/{userId}")
@Slf4j
public class WebSocketServer {
    /**
     * 静态变量 用来记录当前连接数 线程安全
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set,用来存放每个客户端的websocket client 对象
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketServerConcurrentHashMap =
            new ConcurrentHashMap<>();
    /**
     * 与每个客户端的链接会话
     */
    private Session session;

    /**
     * 接受的userId
     */
    private String userId = StringUtils.EMPTY;

    /**
     * 链接建立调用的方法
     *
     * @param session 会话
     * @param userId  用户id
     * @author yujunhong
     * @date 2021/5/18 13:54
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        this.session = session;
        this.userId = userId;
        // 存放websocketServer 到 webSocketServerConcurrentHashMap
        if (webSocketServerConcurrentHashMap.containsKey(userId)) {
            webSocketServerConcurrentHashMap.remove(userId);
            webSocketServerConcurrentHashMap.put(userId, this);
        } else {
            webSocketServerConcurrentHashMap.put(userId, this);
            // 新增在线数量加1
            addOnlineCount();
        }
        log.info("当前用户连接:{},当前在线用户人数:{}", userId, getOnlineCount());

    }

    /**
     * 链接关闭调用的方法
     *
     * @author yujunhong
     * @date 2021/5/18 14:11
     */
    @OnClose
    public void onClose() {
        if (webSocketServerConcurrentHashMap.containsKey(userId)) {
            // 删除该用户的链接
            webSocketServerConcurrentHashMap.remove(userId);
            // 连接数减1
            subOnlineCount();
        }
        log.info("当前用户退出链接:{},当前在线用户人数:{}", userId, getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 信息
     * @param session 会话
     * @author yujunhong
     * @date 2021/5/18 14:13
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:{},报文:{}", userId, message);
        if (StringUtils.isNotEmpty(message)) {
            try {
                // 解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                // 追加发送人信息(防止篡改)
                jsonObject.put("fromUserId", this.userId);
                // 获取发送目标的用户
                String toUserId = jsonObject.getString("toUserId");
                // 传送给对应的toUserId的server
                if (StringUtils.isNotEmpty(toUserId) && webSocketServerConcurrentHashMap.containsKey(userId)) {
                    webSocketServerConcurrentHashMap.get(toUserId).sendMessage(JSON.toJSONString(jsonObject));
                } else {
                    log.error("请求的userId:{}不在该服务器上", toUserId);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session 会话
     * @param error   错误
     * @author yujunhong
     * @date 2021/5/18 14:18
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:{},原因:{}", this.userId, error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     *
     * @param message 发送信息
     * @author yujunhong
     * @date 2021/5/18 14:19
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     *
     * @param message 信息
     * @param userId  用户id
     * @author yujunhong
     * @date 2021/5/18 14:19
     */
    public static void sendInfo(String message, String userId)  {
        try {
            log.info("发送消息到:" + userId + "，报文:" + message);
            if (StringUtils.isNotEmpty(userId) && webSocketServerConcurrentHashMap.containsKey(userId)) {
                webSocketServerConcurrentHashMap.get(userId).sendMessage(message);
            } else {
                log.error("用户:{},不在线！", userId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发自定义消息
     *
     * @param message 信息
     * @author yujunhong
     * @date 2021/5/18 14:19
     */
    public static void groupSendInfo(String message){
        try {
            for (Map.Entry<String, WebSocketServer> entry :
                    webSocketServerConcurrentHashMap.entrySet()) {
                // 获取用户id
                String userId = entry.getKey();
                entry.getValue().sendMessage(message);
                log.info("发送消息到:{}，报文:{}", userId, message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取在线数量
     *
     * @return 在线用户数量
     * @author yujunhong
     * @date 2021/5/18 14:09
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线数量加1
     *
     * @author yujunhong
     * @date 2021/5/18 14:09
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 在线数量减1
     *
     * @author yujunhong
     * @date 2021/5/18 14:09
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
