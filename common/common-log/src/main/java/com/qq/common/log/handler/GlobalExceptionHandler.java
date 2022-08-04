package com.qq.common.log.handler;

import com.qq.common.core.constant.CacheConstants;
import com.qq.common.core.exception.InnerAuthException;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.pojo.log.LogErrorInfo;
import com.qq.common.core.utils.DateUtils;
import com.qq.common.core.utils.StringUtils;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.config.LogConfig;
import com.qq.common.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 全局异常处理器
 *
 * @author qq
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private LogConfig  logConfig;

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        saveLog(requestURI, e.getMessage());
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error("业务异常：{}", e.getMessage());
        Integer code = e.getCode();
        saveLog(request.getRequestURI(), e.getMessage());
        return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        saveLog(requestURI, e.getMessage());
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        saveLog(requestURI, e.getMessage());
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        saveLog("", message);
        return AjaxResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        saveLog("", message);
        return AjaxResult.error(message);
    }

    /**
     * 内部认证异常
     */
    @ExceptionHandler(InnerAuthException.class)
    public AjaxResult handleInnerAuthException(InnerAuthException e) {
        saveLog("", e.getMessage());
        return AjaxResult.error(e.getMessage());
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
        log.info("开始同步异常日志");
        // 错误日志
        LogErrorInfo logErrorInfo = LogErrorInfo.builder()
                .url(requestURI)
                .message(message)
                .time(DateUtils.getTime()).build();
        // 存到redis通过logstash消费保存到es中
        redisService.setCacheList(CacheConstants.ERROR_LOGS_KEY, Arrays.asList(logErrorInfo));
    }
}
