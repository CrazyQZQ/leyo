package com.qq.common.log.pojo;

import lombok.Data;

/**
 * @Description: 日志打印信息
 * @Author QinQiang
 * @Date 2022/4/18
 **/
@Data
public class LogInfo {
    private String ip;
    private String url;
    private String className;
    private String method;
}
