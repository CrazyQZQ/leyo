package com.qq.order.server.service.impl;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.pojo.SysMessage;
import com.qq.order.server.service.MessageFeignService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/8/29
 **/
@Service
public class MessageFeignServiceImpl implements MessageFeignService {
    @Override
    public AjaxResult sendOneMessage(SysMessage message) {
        return AjaxResult.error("系统服务不可用");
    }
}
