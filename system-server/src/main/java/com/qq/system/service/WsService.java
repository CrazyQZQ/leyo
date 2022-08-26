package com.qq.system.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.qq.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/8/25
 **/
@Slf4j
@Service
@ServerEndpoint(value = "/system/websocket/endpoint/{userId}", subprotocols = {"protocol"} )
public class WsService {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    private static CopyOnWriteArraySet<WsService> webSockets = new CopyOnWriteArraySet<>();
    // 用来存在线连接数
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        log.info("【websocket】创建新的连接，userId：{}", userId);
        try {
            Session curSession = sessionPool.get(userId);
            if(ObjectUtil.isNotEmpty(curSession)){
                log.info("【websocket】创建新的连接，当前userId：{} 连接已存在", userId);
                this.session = curSession;
            }else{
                this.session = session;
                webSockets.add(this);
                sessionPool.put(userId, session);
            }
            log.info("【websocket】创建新的连接，总数为：" + webSockets.size());
        } catch (Exception e) {
            log.error("【websocket消息】创建新连接失败，userId：{}", userId);
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            log.info("【websocket消息】连接断开，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端消息:" + message);
    }

    /**
     * 发送错误时的处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误,原因:" + error.getMessage());
        error.printStackTrace();
    }


    // 此为广播消息
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:" + message);
        for (WsService webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给单个用户发消息
     *
     * @param userId
     * @param message
     */
    public void sendOneMessage(Long userId, String message) {
        Session session = sessionPool.get(StrUtil.toString(userId));
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            throw new ServiceException("用户未连接！");
        }
    }

    /**
     * 给多个用户发消息
     * @param userIds
     * @param message
     */
    public void sendMoreMessage(List<Long> userIds, String message) {
        for (Long userId : userIds) {
            sendOneMessage(userId, message);
        }
    }
}
