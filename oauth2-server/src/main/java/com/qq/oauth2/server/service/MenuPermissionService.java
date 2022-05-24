package com.qq.oauth2.server.service;

import com.alibaba.fastjson.JSON;
import com.github.yulichang.query.MPJQueryWrapper;
import com.qq.common.core.constant.AuthConstants;
import com.qq.common.core.constant.CacheConstants;
import com.qq.common.redis.service.RedisService;
import com.qq.common.system.mapper.SysMenuMapper;
import com.qq.common.system.mapper.SysRoleMapper;
import com.qq.common.system.pojo.SysMenu;
import com.qq.common.system.pojo.SysRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 将菜单权限与角色权限绑定，缓存到redis中
 * @Author QinQiang
 * @Date 2022/4/18
 **/
@Service
@Slf4j
public class MenuPermissionService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @PostConstruct
    public void init() {
        List<SysMenu> sysMenus = sysMenuMapper.selectList(null);
        for (SysMenu sysMenu : sysMenus) {
            List<String> roles = sysRoleMapper.selectJoinList(SysRole.class, new MPJQueryWrapper<SysRole>()
                            .select("role_key")
                            .rightJoin("sys_role_menu srm on t.role_id = srm.role_id")
                            .eq("srm.menu_id", sysMenu.getMenuId()))
                    .stream().map(e -> AuthConstants.ROLE_PREFIX + e.getRoleKey()).collect(Collectors.toList());
            redisService.deleteObject(CacheConstants.MENU_ROLES + sysMenu.getPath());
            redisService.setCacheList(CacheConstants.MENU_ROLES + sysMenu.getPath(), roles);
            log.info("缓存菜单权限url：{},权限：{}", sysMenu.getPath(), JSON.toJSONString(roles));
        }
    }
}
