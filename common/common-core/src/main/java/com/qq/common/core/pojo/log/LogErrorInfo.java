package com.qq.common.core.pojo.log;

import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Author Administrator
 * @Date 2022/5/6
 **/
@Data
@Builder
public class LogErrorInfo {
    /**
     * 请求Uri
     */
    private String url;
    /**
     * 异常信息
     */
    private String message;
    /**
     * 时间
     */
    private String time;

}
