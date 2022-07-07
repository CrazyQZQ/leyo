package com.qq.common.system.aspect;

import cn.hutool.core.util.StrUtil;
import com.qq.common.core.annotation.RepeatCommit;
import com.qq.common.core.constant.CacheConstants;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.utils.ServletUtils;
import com.qq.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交切面
 *
 * @author QinQiang
 * @since 2022-05-31 19:08:25
 */
@Aspect
@Component
@Slf4j
public class RepeatCommitAspect {

    private static final String REPEAT_COMMIT_KEY = "repeat_commit_token";

    @Autowired
    private RedisService redisService;

    // 配置织入点
    @Pointcut("@annotation(com.qq.common.core.annotation.RepeatCommit)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            RepeatCommit repeatCommit = method.getAnnotation(RepeatCommit.class);
            ServletUtils.getParameterToInt(REPEAT_COMMIT_KEY);
            String token = ServletUtils.getParameter(REPEAT_COMMIT_KEY);
            long intervals = repeatCommit.Intervals();
            if (StrUtil.isNotEmpty(token)) {
                String key = CacheConstants.REPEAT_COMMIT_KEY_PREFIX + token;
                Boolean hasKey = redisService.hasKey(key);
                if (hasKey) {
                    log.info("重复提交，key：{}", key);
                    throw new ServiceException("数据已提交，请勿重复提交");
                } else {
                    redisService.setCacheObject(key, "1", intervals, TimeUnit.MILLISECONDS);
                }
            }
        }
    }
}
