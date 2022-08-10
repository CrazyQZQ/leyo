package com.qq.order.server.service;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.order.server.service.impl.UserFeignServiceCallBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 用户service
 * @Author QinQiang
 * @Date 2022/4/28
 **/
@Service
@FeignClient(name = "system-server", fallback = UserFeignServiceCallBackImpl.class)
public interface UserFeignService {

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @GetMapping("/system/sysUser/queryAddressById")
    AjaxResult queryAddressById(@RequestParam("id") Long id);

}
