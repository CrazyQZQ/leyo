package com.qq.common.log.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qq.common.core.exception.ServiceException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Description: feign异常装饰
 * @Author QinQiang
 * @Date 2022/7/18
 **/
@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        try {
            String message = Util.toString(response.body().asReader(Charset.forName("utf-8")));
            JSONObject jsonObject = JSON.parseObject(message);
            log.debug(jsonObject.toString());
            return new ServiceException(jsonObject.getString("msg"), jsonObject.getInteger("code"));
        } catch (IOException e) {
            log.debug("转化feign异常信息出错！");
            throw new ServiceException("系统繁忙！");
        }
    }
}
