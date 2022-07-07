package com.qq.common.core.annotation;

import java.lang.annotation.*;

/**
 * @Description: 重复提交
 * @Author QinQiang
 * @Date 2022/5/12
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatCommit {

    /**
     * 间隔时间,单位ms，默认10ms
     */
    long Intervals() default 10;
}
