package com.qq.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysAreaInfo;
import com.qq.system.service.SysAreaInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/17
 **/
@RestController
@RequestMapping("system/area")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysAreaController {

    private final SysAreaInfoService sysAreaInfoService;

    @GetMapping("/list")
    @Log(title = "system_area", funcDesc = "获取区域列表")
    public AjaxResult getAreas(Long parentId) {
        parentId = parentId == null ? 0L : parentId;
        return AjaxResult.success(sysAreaInfoService.list(new QueryWrapper<SysAreaInfo>().eq("parent_id", parentId)));
    }

}
