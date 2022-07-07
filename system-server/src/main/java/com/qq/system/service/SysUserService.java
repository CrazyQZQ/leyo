package com.qq.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.system.pojo.SysUser;
import com.qq.common.system.pojo.SysUserAddress;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户信息表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2022-04-07 19:08:17
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUser queryById(Long userId);

    /**
     * 查询多条数据
     *
     * @param pageNum  查询起始位置
     * @param pageSize 查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(int pageNum, int pageSize);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    SysUser insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    int update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Long userId);

    /**
     * 修改头像
     *
     * @param userId
     * @param file
     */
    void modifyAvatar(Long userId, MultipartFile file);

    /**
     * 新增用户地址
     *
     * @param address
     */
    void addUserAddress(SysUserAddress address);

    /**
     * 修改用户地址
     *
     * @param address
     */
    void modifyUserAddress(SysUserAddress address);

    /**
     * 查询用户地址
     *
     * @param userId
     */
    List<SysUserAddress> queryUserAddress(Long userId);

    /**
     * 删除用户地址
     *
     * @param id
     */
    void deleteUserAddress(Long id);

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    SysUserAddress queryAddressById(Long id);
}