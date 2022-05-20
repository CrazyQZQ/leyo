package com.qq.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.qq.common.system.mapper.SysObjectImagesMapper;
import com.qq.common.system.mapper.SysUserMapper;
import com.qq.common.system.pojo.SysObjectImages;
import com.qq.common.system.pojo.SysUser;
import com.qq.common.system.service.MinIoService;
import com.qq.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2022-04-07 19:08:22
 */
@Service("sysUserService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper userMapper;
    private final MinIoService minIoService;
    private final SysObjectImagesMapper sysObjectImagesMapper;

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
        SysObjectImages sysObjectImages = sysObjectImagesMapper.selectOne(new QueryWrapper<SysObjectImages>()
                .eq("object_id", userId)
                .eq("object_type", 0));
        if (sysObjectImages != null) {
            minIoService.deleteFileByFullPath(Arrays.asList(sysObjectImages.getImageUrl()));
            sysObjectImagesMapper.delete(new QueryWrapper<SysObjectImages>()
                    .eq("object_id", userId));
        }
        String url = minIoService.upload(file);
        SysObjectImages objectImages = new SysObjectImages();
        objectImages.setObjectId(userId);
        objectImages.setObjectType(0);
        objectImages.setImageUrl(url);
        sysObjectImagesMapper.insert(objectImages);
    }
}