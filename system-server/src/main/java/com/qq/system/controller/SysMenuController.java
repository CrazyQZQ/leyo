package com.qq.system.controller;

import com.alibaba.fastjson.JSON;
import com.qq.common.core.constant.AuthConstants;
import com.qq.common.core.constant.CacheConstants;
import com.qq.common.core.utils.PageUtils;
import com.qq.common.core.utils.uuid.IdUtils;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysMenu;
import com.qq.common.system.pojo.SysRoleMenu;
import com.qq.common.system.service.SysMenuService;
import com.qq.common.redis.service.RedisService;
import com.qq.system.domain.MenuVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:  
 * @Author QinQiang
 * @Date 2022/4/29 
**/
@RestController
@RequestMapping("system/sysMenu")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysMenuController {

    private final SysMenuService sysMenuService;
    private final RedisService redisService;

    @Log(title = "system", funcDesc = "获取菜单列表")
    @GetMapping("list")
    public AjaxResult selectAll() {
        PageUtils.startPage();
        return AjaxResult.success(sysMenuService.list());
    }

    @Log(title = "system", funcDesc = "新增菜单")
    @PostMapping("add")
    public AjaxResult addMenu(@RequestBody SysMenu sysMenu) {
        sysMenuService.addMenu(sysMenu);
        log.info("新增菜单：{}", JSON.toJSONString(sysMenu));
        return AjaxResult.success();
    }

    @Log(title = "system", funcDesc = "分配菜单角色")
    @PostMapping("auth")
    public AjaxResult authMenu(@RequestBody MenuVo menuVo) {
        sysMenuService.authMenu(menuVo.getSysMenu().getMenuId(), menuVo.getSysRoleMenus());
        List<String> roles = menuVo.getSysRoleMenus().stream().map(e -> AuthConstants.ROLE_PREFIX + e.getRoleKey()).collect(Collectors.toList());
        redisService.deleteObject(CacheConstants.MENU_ROLES + menuVo.getSysMenu().getPath());
        redisService.setCacheList(CacheConstants.MENU_ROLES + menuVo.getSysMenu().getPath(), roles);
        log.info("新增菜单：{}, 分配角色：{}", menuVo.getSysMenu().getPath(), JSON.toJSONString(roles));
        return AjaxResult.success();
    }
}
