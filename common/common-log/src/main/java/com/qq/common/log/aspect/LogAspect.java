package com.qq.common.log.aspect;

import com.alibaba.fastjson.JSON;
import com.qq.common.core.utils.ServletUtils;
import com.qq.common.core.utils.ip.IpUtils;
import com.qq.common.log.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

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
            StringBuffer logMessage = new StringBuffer();
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            String title = controllerLog.title();
            logMessage.append("模块: ").append(title).append(",");
            // *========数据库日志=========*//
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            logMessage.append("请求地址: ").append(ip).append(",");


            String requestURI = ServletUtils.getRequest().getRequestURI();
            logMessage.append("请求Uri: ").append(requestURI).append(",");
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String classMethod = className + "." + methodName + "()";
            logMessage.append("请求方法: ").append(classMethod).append(",");
            // 返回参数
            logMessage.append("返回参数: ").append(JSON.toJSONString(jsonResult));
            log.info(logMessage.toString());
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
