package com.qq.nacos_customer.controller;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.pojo.SysUser;
import com.qq.common.system.service.SysRoleService;
import com.qq.common.system.service.SysUserService;
import com.qq.common.system.utils.OauthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户信息表(SysUser)表控制层
 *
 * @author makejava
 * @since 2022-04-07 19:08:25
 */
@RestController
@RequestMapping("customer/sysUser")
@Slf4j
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult selectOne(@PathVariable("id") Long id) {
        return AjaxResult.success(sysUserService.queryById(id));
    }


    @GetMapping("list")
    public AjaxResult selectAll(int pageNum, int pageSize) {
        SysUser currentUser = OauthUtils.getCurrentUser();
        log.info("currentUser:{}",currentUser);
        return AjaxResult.success(sysUserService.queryAllByLimit(pageNum,pageSize));
    }

    @PostMapping("update")
    public AjaxResult update(@RequestBody SysUser user) {
        return AjaxResult.success(sysUserService.update(user));
    }

    @PutMapping("add")
    public AjaxResult add(@RequestBody SysUser user) {
        return AjaxResult.success(sysUserService.insert(user));
    }

    @GetMapping("getRolesByUser/{userId}")
    public AjaxResult getRolesByUser(@PathVariable("userId") Long userId){
        return AjaxResult.success(sysRoleService.getByUser(userId));
    }
}