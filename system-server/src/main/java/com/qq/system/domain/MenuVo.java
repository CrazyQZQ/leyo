package com.qq.system.domain;

import com.qq.common.system.pojo.SysMenu;
import com.qq.common.system.pojo.SysRoleMenu;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/29
 **/
@Data
public class MenuVo {
    private SysMenu sysMenu;
    private List<SysRoleMenu> sysRoleMenus;
}
