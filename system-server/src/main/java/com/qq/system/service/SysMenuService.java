package com.qq.system.service;

import com.qq.common.system.pojo.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.system.pojo.SysRoleMenu;

import java.util.List;

/**
* @author QinQiang
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2022-04-29 14:43:18
*/
public interface SysMenuService extends IService<SysMenu> {

    void addMenu(SysMenu sysMenu);

    void authMenu(Long menuId, List<SysRoleMenu> sysRoleMenus);
}
