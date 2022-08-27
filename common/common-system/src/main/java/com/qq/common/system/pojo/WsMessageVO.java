package com.qq.common.system.pojo;

import lombok.Data;

/**
 * @Description: webSocket消息封装
 * @Author QinQiang
 * @Date 2022/8/25
 **/
@Data
public class WsMessageVO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 操作 ping：来自客户端的心跳，pong：响应给客户端的心跳
     */
    private String action;
    /**
     * 消息体
     */
    private String body;
    /**
     * 消息类型 primary success warning
     */
    private String type;
}
