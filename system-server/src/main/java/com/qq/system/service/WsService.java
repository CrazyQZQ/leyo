package com.qq.system.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.redis.service.RedisService;
import com.qq.common.system.pojo.WsMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
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
@ServerEndpoint("/system/websocket/endpoint/{userId}")
public class WsService {
    @Autowired
    private RedisService redisService;
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
            this.session = session;
            webSockets.add(this);
            sessionPool.put(userId, session);
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
        WsMessageVO wsMessageVO = JSON.parseObject(message, WsMessageVO.class);
        log.info("【websocket消息】收到客户端消息:" + message);
        Session session = null;
        for (Map.Entry<String, Session> entry : sessionPool.entrySet()) {
            if (entry.getKey().equals(StrUtil.toString(wsMessageVO.getUserId()))) {
                session = entry.getValue();
            }
        }
        if (session != null) {
            if (session.isOpen()) {
                // 心跳消息
                if ("ping".equals(wsMessageVO.getAction())) {
                    WsMessageVO response = new WsMessageVO();
                    response.setUserId(wsMessageVO.getUserId());
                    response.setAction("pong");
                    response.setBody(message);
                    response.setType("primaty");
                    sendOneMessage(response);
                }
            } else {
                log.warn("【websocket收到消息】用户（id：{}）连接已关闭:", wsMessageVO.getUserId());
            }
        } else {
            log.warn("【websocket收到消息】用户（id：{}）未连接:", wsMessageVO.getUserId());
        }
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

    /**
     * 广播消息
     *
     * @param message
     */
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
     * @param messageVO
     */
    public void sendOneMessage(WsMessageVO messageVO) {
        Session session = null;
        for (Map.Entry<String, Session> entry : sessionPool.entrySet()) {
            if (entry.getKey().equals(StrUtil.toString(messageVO.getUserId()))) {
                session = entry.getValue();
            }
        }
        if (session != null && session.isOpen()) {
            try {
                String messageJsonStr = JSON.toJSONString(messageVO);
                log.info("【websocket消息】 单点消息:" + messageJsonStr);
                session.getAsyncRemote().sendText(messageJsonStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new ServiceException("用户未连接！");
        }
    }

    /**
     * 给多个用户发消息
     *
     * @param messageVOs
     */
    public void sendMoreMessage(List<WsMessageVO> messageVOs) {
        for (WsMessageVO messageVO : messageVOs) {
            sendOneMessage(messageVO);
        }
    }
}
