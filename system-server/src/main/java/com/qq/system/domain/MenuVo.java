package com.qq.system.domain;

import com.qq.common.system.pojo.SysMenu;
import com.qq.common.system.pojo.SysRoleMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/29
 **/
@Data
@ApiModel("分配菜单参数")
public class MenuVo {
    @ApiModelProperty("菜单信息")
    private SysMenu sysMenu;
    @ApiModelProperty("角色信息")
    private List<SysRoleMenu> sysRoleMenus;
}
