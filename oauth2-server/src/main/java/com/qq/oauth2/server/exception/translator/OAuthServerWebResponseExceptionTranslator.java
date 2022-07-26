package com.qq.oauth2.server.exception.translator;

import com.qq.common.core.constant.CacheConstants;
import com.qq.common.core.enums.ExceptionCode;
import com.qq.common.core.pojo.log.LogErrorInfo;
import com.qq.common.core.utils.DateUtils;
import com.qq.common.core.utils.SpringUtils;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.config.LogConfig;
import com.qq.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@Slf4j
@Component
public class OAuthServerWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Autowired
    private RedisService redisService;
    @Autowired
    private LogConfig logConfig;
    @Override
    public ResponseEntity<AjaxResult> translate(Exception e) {
        ExceptionCode resultCode = doTranslate(e);
        saveLog("/oauth/**", resultCode.getMsg());
        return new ResponseEntity<>(AjaxResult.error(resultCode.getCode(), resultCode.getMsg()), HttpStatus.UNAUTHORIZED);
    }

    private ExceptionCode doTranslate(Exception e) {
        ExceptionCode resultCode = ExceptionCode.UNAUTHORIZED;
        if (e instanceof UnsupportedGrantTypeException) {
            resultCode = ExceptionCode.UNSUPPORTED_GRANT_TYPE;
        } else if (e instanceof InvalidGrantException) {
            resultCode = ExceptionCode.USERNAME_OR_PASSWORD_ERROR;
        } else if (e instanceof InvalidClientException) {
            resultCode = ExceptionCode.UNSUPPORTED_GRANT_TYPE;
        }
        return resultCode;
    }

    /**
     * 保存日志
     *
     * @param requestURI
     * @param message
     */
    private void saveLog(String requestURI, String message) {
        if (!"1".equals(logConfig.getSyncLog())) {
            return;
        }
        log.info("oauth2-server开始同步异常日志");
        // 错误日志
        LogErrorInfo logErrorInfo = LogErrorInfo.builder()
                .url(requestURI)
                .message(message)
                .time(DateUtils.getTime()).build();
        // 存到redis通过logstash消费保存到es中
        redisService.setCacheList(CacheConstants.ERROR_LOGS_KEY, Arrays.asList(logErrorInfo));
    }
}
