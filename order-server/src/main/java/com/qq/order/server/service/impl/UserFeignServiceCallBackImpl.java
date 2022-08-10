package com.qq.order.server.service.impl;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.order.server.service.UserFeignService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/6/2
 **/
@Service
public class UserFeignServiceCallBackImpl implements UserFeignService {

    @Override
    public AjaxResult queryAddressById(Long id) {
        return AjaxResult.error("系统服务不可用");
    }
}

