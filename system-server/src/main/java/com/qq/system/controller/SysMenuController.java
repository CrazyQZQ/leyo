package com.qq.system.controller;

import com.qq.common.core.constant.CacheConstants;
import com.qq.common.core.utils.PageUtils;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysRoleMenu;
import com.qq.common.system.service.SysMenuService;
import com.qq.common.redis.service.RedisService;
import com.qq.system.domain.MenuVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("add")
    public AjaxResult addMenu(@RequestBody MenuVo menuVo) {
        sysMenuService.addMenu(menuVo.getSysMenu(), menuVo.getSysRoleMenus());
        List<String> roles = menuVo.getSysRoleMenus().stream().map(SysRoleMenu::getRoleKey).collect(Collectors.toList());
        redisService.deleteObject(CacheConstants.MENU_ROLES + menuVo.getSysMenu());
        redisService.setCacheList(CacheConstants.MENU_ROLES + menuVo.getSysMenu(), roles);
        return AjaxResult.success(sysMenuService.list());
    }
}
