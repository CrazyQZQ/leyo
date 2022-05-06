package com.qq.common.log.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: 日志打印信息
 * @Author QinQiang
 * @Date 2022/4/18
 **/
@Data
@Builder
public class LogInfo {

    /**
     * ip
     */
    private String ip;
    /**
     * 请求Uri
     */
    private String url;
    /**
     * 模块
     */
    private String module;
    /**
     * 功能描述
     */
    private String funcDesc;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 返回参数
     */
    private String response;
    /**
     * 时间
     */
    private String time;
}
