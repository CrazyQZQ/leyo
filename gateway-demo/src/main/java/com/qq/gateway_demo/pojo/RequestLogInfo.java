package com.qq.gateway_demo.pojo;

import lombok.Data;

/**
 * @Description: 请求日志信息
 * @Author QinQiang
 * @Date 2022/4/18
 **/
@Data
public class RequestLogInfo {
    /**
     * 请求id
     */
    private String requestId;
    /**
     * 请求ip
     */
    private String remoteAddr;
    /**
     * 请求时间
     */
    private String requestTime;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求地址
     */
    private String requestUrl;
    /**
     * 请求参数
     */
    private String requestParam;
    /**
     * 请求结果
     */
    private String requestResult;
    /**
     * 请求体
     */
    private String requestBody;
    /**
     * 请求耗时
     */
    private Long responseTime;
    /**
     * 响应状态码
     */
    private String responseCode;
    /**
     * 响应体
     */
    private String responseBody;
}

