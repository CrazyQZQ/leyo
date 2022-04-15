package com.qq.gateway_demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@ConfigurationProperties(prefix = "oauth2.cloud.sys.parameter")
@Data
@Component
public class SysParameterConfig {
    /**
     * 白名单
     */
    private List<String> ignoreUrls;
}
