package com.qq.common.log.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Description: log配置
 * @Author QinQiang
 * @Date 2022/7/26
 **/
@RefreshScope
@Component
@Data
public class LogConfig {
    @Value("${log.sync.es:0}")
    private String syncLog;
}
