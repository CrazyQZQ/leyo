package com.qq.common.log.aspect;

import com.alibaba.fastjson.JSON;
import com.qq.common.core.constant.CacheConstants;
import com.qq.common.core.utils.DateUtils;
import com.qq.common.core.utils.ServletUtils;
import com.qq.common.core.utils.ip.IpUtils;
import com.qq.common.log.annotation.Log;
import com.qq.common.log.pojo.LogInfo;
import com.qq.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Value("${log.sync.es:0}")
    private String syncLog;

    @Autowired
    private RedisService redisService;

    // 配置织入点
    @Pointcut("@annotation(com.qq.common.log.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            String module = controllerLog.title();
            String funcDesc = controllerLog.funcDesc();
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            String requestURI = ServletUtils.getRequest().getRequestURI();
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String classMethod = className + "." + methodName + "()";
            // 返回参数
            LogInfo logInfo = LogInfo.builder()
                    .module(module)
                    .funcDesc(funcDesc)
                    .ip(ip)
                    .url(requestURI)
                    .method(classMethod)
                    .response(JSON.toJSONString(jsonResult))
                    .time(DateUtils.getTime())
                    .build();
            // 存到redis通过logstash消费保存到es中
            if ("1".equals(syncLog)) {
                redisService.setCacheList(CacheConstants.LOGS_KEY, Arrays.asList(logInfo));
            }
            log.info(JSON.toJSONString(logInfo));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
