package com.qq.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.utils.StringUtils;
import com.qq.common.system.mapper.SysMenuMapper;
import com.qq.common.system.mapper.SysUserAddressMapper;
import com.qq.common.system.mapper.SysUserMapper;
import com.qq.common.system.pojo.SysMenu;
import com.qq.common.system.pojo.SysUser;
import com.qq.common.system.pojo.SysUserAddress;
import com.qq.common.system.service.MinIoService;
import com.qq.common.system.utils.OauthUtils;
import com.qq.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-04-07 19:08:22
 */
@Service("sysUserService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper userMapper;
    private final MinIoService minIoService;
    private final SysUserAddressMapper userAddressMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public SysUser queryById(Long userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 查询多条数据
     *
     * @param pageNum  查询起始位置
     * @param pageSize 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysUser> queryAllByLimit(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectList(null);
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser insert(SysUser sysUser) {
        userMapper.insert(sysUser);
        return sysUser;
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SysUser sysUser) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("userId", sysUser.getUserId());
        int update = userMapper.update(sysUser, updateWrapper);
        return update;
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long userId) {
        return userMapper.deleteById(userId) > 0;
    }

    /**
     * 修改头像
     *
     * @param userId
     * @param file
     */
    @Override
    public void modifyAvatar(Long userId, MultipartFile file) {
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().select("user_id", "avatar").eq("user_id", userId));
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        if (StringUtils.isNotEmpty(user.getAvatar())) {
            minIoService.deleteFileByFullPath(Arrays.asList(user.getAvatar()));
        }
        String url = minIoService.upload(file);
        user.setAvatar(url);
        userMapper.updateById(user);
    }

    /**
     * 新增用户地址
     *
     * @param address
     */
    @Override
    public void addUserAddress(SysUserAddress address) {
        address.setCreateBy(OauthUtils.getCurrentUserName());
        address.setCreateTime(new Date());
        if (ObjectUtil.isNotEmpty(address.getDefaultStatus()) && address.getDefaultStatus() == 1) {
            SysUserAddress updateAddress = new SysUserAddress();
            updateAddress.setDefaultStatus(0);
            userAddressMapper.update(updateAddress, new UpdateWrapper<SysUserAddress>().eq("default_status", 1));
        }
        userAddressMapper.insert(address);
    }

    /**
     * 修改用户地址
     *
     * @param address
     */
    @Override
    public void modifyUserAddress(SysUserAddress address) {
        address.setUpdateBy(OauthUtils.getCurrentUserName());
        address.setUpdateTime(new Date());
        if (ObjectUtil.isNotEmpty(address.getDefaultStatus()) && address.getDefaultStatus() == 1) {
            SysUserAddress updateAddress = new SysUserAddress();
            updateAddress.setDefaultStatus(0);
            userAddressMapper.update(updateAddress, new UpdateWrapper<SysUserAddress>().eq("default_status", 1));
        }
        userAddressMapper.updateById(address);
    }

    /**
     * 查询用户地址
     *
     * @param userId
     */
    @Override
    public List<SysUserAddress> queryUserAddress(Long userId) {
        return userAddressMapper.selectList(new QueryWrapper<SysUserAddress>().eq("user_id", userId));
    }

    /**
     * 删除用户地址
     *
     * @param id
     */
    @Override
    public void deleteUserAddress(Long id) {
        userAddressMapper.deleteById(id);
    }

    @Override
    public SysUserAddress queryAddressById(Long id) {
        return userAddressMapper.selectById(id);
    }
}