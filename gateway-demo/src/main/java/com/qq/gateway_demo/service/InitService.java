package com.qq.gateway_demo.service;

import com.google.common.collect.Lists;
import com.qq.common_redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 用于初始化uir的权限到redis中
 * TODO 实际开发中需要自己维护，此处只是为了演示方便
 * 详情见 cn.myjszl.oauth2.cloud.auth.server.service.impl.LoadRolePermissionService
 */
@Service
public class InitService {
    @Autowired
    private RedisService redisService;

    @PostConstruct
    public void init(){
        redisService.setCacheList("/user/userInfo",Lists.newArrayList("ROLE_admin","ROLE_user"));
        redisService.setCacheList("/user/adminInfo",Lists.newArrayList("ROLE_admin"));
        redisService.setCacheList("/customer/sysUser/list",Lists.newArrayList("ROLE_admin","ROLE_user"));
        redisService.setCacheList("/oauth/user/loginUser",Lists.newArrayList("ROLE_admin"));
    }

}
