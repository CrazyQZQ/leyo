package com.qq.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.common.core.constant.CacheConstants;
import com.qq.common.redis.service.RedisService;
import com.qq.common.system.mapper.SysObjectImagesMapper;
import com.qq.common.system.pojo.SysObjectImages;
import com.qq.common.system.service.MinIoService;
import com.qq.system.service.HomeSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/24
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeSettingServiceImpl implements HomeSettingService {

    private final MinIoService minIoService;
    private final SysObjectImagesMapper sysObjectImagesMapper;
    private final RedisService redisService;

    @Override
    public List<SysObjectImages> getBanners() {
        List<SysObjectImages> objectImages = Optional
                .ofNullable(sysObjectImagesMapper.selectList(new QueryWrapper<SysObjectImages>().eq("object_type", 4)))
                .orElse(Collections.emptyList());
        return objectImages;
    }

    @Override
    @Transactional
    public void addBanner(MultipartFile[] files) {
        for (MultipartFile file : files) {
            SysObjectImages sysObjectImages = new SysObjectImages();
            sysObjectImages.setObjectType(4);
            sysObjectImages.setImageUrl(minIoService.upload(file));
            sysObjectImagesMapper.insert(sysObjectImages);
        }
    }

    @Override
    public void deleteBanner(Long id) {
        SysObjectImages sysObjectImages = sysObjectImagesMapper.selectById(id);
        if(sysObjectImages != null){
            minIoService.deleteFileByFullPath(Arrays.asList(sysObjectImages.getImageUrl()));
            sysObjectImagesMapper.deleteById(id);
        }
    }

    @Override
    public void createAnnouncement(String content) {
        redisService.setCacheObject(CacheConstants.ANNOUNCEMENT_KEY, content);
    }

    @Override
    public String getAnnouncement() {
        return redisService.getCacheObject(CacheConstants.ANNOUNCEMENT_KEY);
    }
}
