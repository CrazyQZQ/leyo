package com.qq.common.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 * @author ruoyi
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatCommit {

    /**
     * 间隔时间,单位ms，默认10ms
     */
    long Intervals() default 10;
}
