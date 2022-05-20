package com.qq.system.controller;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysUser;
import com.qq.system.service.SysRoleService;
import com.qq.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息表(SysUser)表控制层
 *
 * @author makejava
 * @since 2022-04-07 19:08:25
 */
@RestController
@RequestMapping("system/sysUser")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserController {
    /**
     * 服务对象
     */
    private final SysUserService sysUserService;

    /**
     * 服务对象
     */
    private final SysRoleService sysRoleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("getByid")
    @Log(title = "system", funcDesc = "查询单个用户")
    public AjaxResult selectOne(@RequestParam("id") Long id) {
        return AjaxResult.success(sysUserService.queryById(id));
    }


    @Log(title = "system", funcDesc = "获取用户列表")
    @GetMapping("list")
    public AjaxResult selectAll(int pageNum, int pageSize) {
        return AjaxResult.success(sysUserService.queryAllByLimit(pageNum,pageSize));
    }

    @Log(title = "system", funcDesc = "修改用户信息")
    @PostMapping("update")
    public AjaxResult update(@RequestBody SysUser user) {
        return AjaxResult.success(sysUserService.update(user));
    }

    @Log(title = "system", funcDesc = "新增用户")
    @PutMapping("add")
    public AjaxResult add(@RequestBody SysUser user) {
        return AjaxResult.success(sysUserService.insert(user));
    }

    @Log(title = "system", funcDesc = "获取用户角色")
    @GetMapping("getRolesByUser")
    public AjaxResult getRolesByUser(@RequestParam("userId") Long userId){
        return AjaxResult.success(sysRoleService.getByUser(userId));
    }

    @Log(title = "system", funcDesc = "修改用户头像")
    @GetMapping("modifyAvatar")
    public AjaxResult modifyAvatar(@RequestParam("userId") Long userId,@RequestParam("file") MultipartFile file){
        sysUserService.modifyAvatar(userId, file);
        return AjaxResult.success();
    }
}