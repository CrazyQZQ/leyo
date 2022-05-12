package com.qq.system.controller;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.service.MinIoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 *
 * @author makejava
 * @since 2022-04-07 19:08:25
 */
@RestController
@RequestMapping("system/file")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {
    /**
     * 服务对象
     */
    private final MinIoService minIoService;

    @PostMapping("upload")
    public AjaxResult upload(@RequestParam("file") MultipartFile file) {
        return AjaxResult.success(minIoService.upload(file));
    }

    /**
     *
     * @param fileName 123.png
     * @param objectName 20220507/123.png
     * @param response
     */
    @GetMapping("download")
    public void download(String fileName, String objectName, HttpServletResponse response) {
        minIoService.download(fileName, objectName, response);
    }

    @PostMapping("delete")
    public AjaxResult delete(@RequestBody List<String> objectNames) {
        minIoService.deleteFile(objectNames);
        return AjaxResult.success();
    }

    @GetMapping("list")
    public AjaxResult list() {
        return AjaxResult.success(minIoService.listFile());
    }
}