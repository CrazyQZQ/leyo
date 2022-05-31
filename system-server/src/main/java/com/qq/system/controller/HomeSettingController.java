package com.qq.system.controller;

import cn.hutool.core.util.IdUtil;
import com.qq.common.core.constant.CacheConstants;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.system.service.HomeSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 首页配置
 * @Author QinQiang
 * @Date 2022/5/24
 **/
@RestController
@RequestMapping("system/home")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeSettingController {
    private final HomeSettingService homeSettingService;

    /**
     * 获取banners
     *
     * @return
     */
    @GetMapping("/banners")
    @Log(title = "system_home", funcDesc = "获取banners")
    public AjaxResult getBanners() {
        return AjaxResult.success(homeSettingService.getBanners());
    }

    /**
     * 添加banner
     *
     * @param files
     * @return
     */
    @PutMapping("/banners")
    @Log(title = "system_home", funcDesc = "添加banner")
    public AjaxResult addBanner(MultipartFile[] files) {
        homeSettingService.addBanner(files);
        return AjaxResult.success();
    }

    /**
     * 删除banner
     *
     * @param id
     * @return
     */
    @DeleteMapping("/banners")
    @Log(title = "system_home", funcDesc = "删除banner")
    public AjaxResult deleteBanner(Long id) {
        homeSettingService.deleteBanner(id);
        return AjaxResult.success();
    }

    /**
     * 发布公告
     *
     * @param content
     * @return
     */
    @PutMapping("/announcement")
    @Log(title = "system_home", funcDesc = "发布公告")
    public AjaxResult createAnnouncement(String content) {
        homeSettingService.createAnnouncement(content);
        return AjaxResult.success();
    }

    /**
     * 获取公告
     *
     * @return
     */
    @GetMapping("/announcement")
    @Log(title = "system_home", funcDesc = "获取公告")
    public AjaxResult getAnnouncement() {
        return AjaxResult.success(homeSettingService.getAnnouncement());
    }

    /**
     * 获取重复提交token
     *
     * @return
     */
    @GetMapping("/repeatCommitToken")
    @Log(title = "system_home", funcDesc = "获取重复提交token")
    public AjaxResult getRepeatCommitToken() {
        return AjaxResult.success(CacheConstants.REPEAT_COMMIT_KEY_PREFIX + IdUtil.getSnowflakeNextId());
    }
}
