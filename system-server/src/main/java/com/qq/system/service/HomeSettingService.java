package com.qq.system.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HomeSettingService {

    /**
     * 获取banners
     * @return
     */
    List<String> getBanners();
    /**
     * 添加banner
     * @param files
     */
    void addBanner(MultipartFile[] files);
    /**
     * 删除
     * @param id
     */
    void deleteBanner(Long id);

    /**
     * 发布公告
     * @param content
     */
    void createAnnouncement(String content);

    /**
     * 获取公告
     */
    String getAnnouncement();
}
