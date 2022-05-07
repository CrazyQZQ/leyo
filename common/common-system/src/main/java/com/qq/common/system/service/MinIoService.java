package com.qq.common.system.service;

import com.qq.common.system.vo.FileVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description: MinIo上传文件
 * @Author qq
 * @Date 2022/5/7
 **/
public interface MinIoService {
    /**
     * @Description: 上传文件
     */
    String upload(MultipartFile file);

    /**
     * @Description: 下载文件
     * @param fileName 文件名
     * @param objectName 对象名
     * @param response
     */
    void download(String fileName, String objectName, HttpServletResponse response);

    /**
     * @Description: 删除文件
     * @param objectNames 对象名
     */
    void deleteFile(List<String> objectNames);

    /**
     * @Description: 获取文件列表
     */
    List<FileVO> listFile();
}
