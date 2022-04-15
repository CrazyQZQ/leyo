package com.qq.nacos_customer.controller;

import com.qq.common_redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/6
 **/
@RestController
@RequestMapping("customer/cache")
public class CacheController {

    @Autowired
    private RedisService redisService;

    @GetMapping("get/{key}")
    public String get(@PathVariable String key) {
        return redisService.getCacheObject(key);
    }

    @PostMapping("set")
    public void set(@RequestParam("key") String key, @RequestParam("value") String value) {
        redisService.setCacheObject(key, value);
    }
}
