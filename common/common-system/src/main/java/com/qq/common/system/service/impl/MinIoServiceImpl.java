package com.qq.common.system.service.impl;

import com.qq.common.core.utils.DateUtils;
import com.qq.common.core.utils.file.ImageUtils;
import com.qq.common.core.utils.file.MimeTypeUtils;
import com.qq.common.system.service.MinIoService;
import com.qq.common.system.vo.FileVO;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author Administrator
 * @Date 2022/5/7
 **/
@Service
@Slf4j
public class MinIoServiceImpl implements MinIoService {

    private MinioClient minioClient;


    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    public MinioClient getMinioClient() throws InvalidPortException, InvalidEndpointException {
        if(minioClient == null){
            minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
        }
        return minioClient;
    }
    @Override
    public String upload(MultipartFile file) {
        try {
            //创建一个MinIO的Java客户端
            MinioClient minioClient = getMinioClient();
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(BUCKET_NAME);
            if (isExist) {
                log.info("存储桶已经存在！");
            } else {
                //创建存储桶
                minioClient.makeBucket(BUCKET_NAME);
            }
            String filename = file.getOriginalFilename();
            // 设置存储对象名称
            String objectName = DateUtils.getDate() + "/" + filename;
            // 使用putObject上传一个文件到存储桶中
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(BUCKET_NAME,objectName, inputStream,new PutObjectOptions(inputStream.available(),-1));
            log.info("文件上传成功!");
            return ENDPOINT + "/" + BUCKET_NAME + "/" + objectName;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("上传发生错误: {}！", e.getMessage());
        }
        return null;
    }

    @Override
    public void download(String fileName, String objectName, HttpServletResponse response) {
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            // 设置response的Header
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");

            OutputStream os = response.getOutputStream();
            MinioClient minioClient = getMinioClient();
            // 获取文件输入流
            inputStream = minioClient.getObject(BUCKET_NAME, objectName);

            bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] bytes = new byte[2048];
            int len;
            while((len = bufferedInputStream.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
        } catch (Exception e) {
            log.error("下载文件发生错误: ", e);
        }finally {
            if(bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    log.error("下载文件发生错误: ", e);
                }
            }
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("下载文件发生错误: ", e);
                }
            }
        }
    }

    @Override
    public void deleteFile(List<String> objectNames) {
        try {
            MinioClient minioClient = getMinioClient();
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(BUCKET_NAME, objectNames);
            for (Result<DeleteError> result : results) {
                DeleteError deleteError = result.get();
                log.debug(deleteError.message());
            }
        } catch (Exception e) {
            log.error("删除文件发生错误: ", e);
        }
    }

    @Override
    public List<FileVO> listFile() {
        List<FileVO> fileVOS = new ArrayList<>();
        try {
            MinioClient minioClient = getMinioClient();
            Iterable<Result<Item>> results = minioClient.listObjects(BUCKET_NAME);
            for (Result<Item> result : results) {
                Item item = result.get();
                ZonedDateTime zonedDateTime = item.lastModified().withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
                FileVO fileVO = new FileVO();
                fileVO.setObjectName(item.objectName());
                fileVO.setUploadTime(zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                if(ImageUtils.isImage(item.objectName())) {
                    fileVO.setPreviewUrl(ENDPOINT + "/" + BUCKET_NAME + "/" + item.objectName());
                }
                fileVOS.add(fileVO);

            }
        } catch (Exception e) {
            log.error("查询文件列表发生错误: ", e);
        }
        return fileVOS;
    }


}
