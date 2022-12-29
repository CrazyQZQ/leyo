package com.qq.order.server.service;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.pojo.SysMessage;
import com.qq.order.server.service.impl.MessageFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 消息推送
 * @Author QinQiang
 * @Date 2022/8/29
 **/
@Service
@FeignClient(name = "system-server", fallback = MessageFeignServiceImpl.class)
public interface MessageFeignService {

    /**
     * 给单个用户发消息
     *
     * @param message
     */
    @ResponseBody
    @PostMapping(value = "/system/message/send/one", consumes = "application/json")
    AjaxResult sendOneMessage(@RequestBody SysMessage message);
}
