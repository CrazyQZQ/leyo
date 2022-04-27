package com.qq.account.config;

import com.qq.common.log.aspect.LogAspect;
import com.qq.common_redis.service.RedisService;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableDiscoveryClient
@Configuration
public class BaseConfig {

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean
    public RedisService redisService() {
        return new RedisService();
    }

}
