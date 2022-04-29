package comqq.common.es.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/26
 **/
@ConfigurationProperties(prefix = "elastic.search")
@Data
@Component
public class EsConfig {
    private String host;
    private int port;
    private String username;
    private String password;
}
