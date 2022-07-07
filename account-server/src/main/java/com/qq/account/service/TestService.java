package com.qq.account.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/2
 **/
@Service
@FeignClient(name = "system-server", fallback = CallBackServiceImpl.class)
public interface TestService {
    /**
     * 测试
     * @param str
     * @return
     */
    @GetMapping("provide/echo/{str}")
    String echo(@PathVariable("str") String str);
}
