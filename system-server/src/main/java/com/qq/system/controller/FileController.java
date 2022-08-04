package com.qq.system.controller;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.service.MinIoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author makejava
 * @since 2022-04-07 19:08:25
 */
@RestController
@RequestMapping("system/file")
@Slf4j
@Api(tags = "文件管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {
    /**
     * 服务对象
     */
    private final MinIoService minIoService;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @ApiOperation("上传文件")
    @PutMapping("upload")
    public AjaxResult upload(@ApiParam("文件") @RequestParam("file") MultipartFile file) {
        return AjaxResult.success(minIoService.upload(file));
    }

    /**
     * 下载文件
     *
     * @param fileName   123.png
     * @param objectName 20220507/123.png
     * @param response
     */
    @ApiOperation("下载文件")
    @GetMapping("download")
    public void download(@ApiParam("文件名") String fileName, @ApiParam("对象名") String objectName, HttpServletResponse response) {
        minIoService.download(fileName, objectName, response);
    }

    /**
     * 删除文件
     *
     * @param objectNames
     * @return
     */
    @ApiOperation("删除文件")
    @PutMapping("delete")
    public AjaxResult delete(@ApiParam("对象名") @RequestBody List<String> objectNames) {
        minIoService.deleteFile(objectNames);
        return AjaxResult.success();
    }

    /**
     * 获取文件列表
     *
     * @return
     */
    @ApiOperation("获取文件列表")
    @GetMapping("list")
    public AjaxResult list() {
        return AjaxResult.success(minIoService.listFile());
    }
}