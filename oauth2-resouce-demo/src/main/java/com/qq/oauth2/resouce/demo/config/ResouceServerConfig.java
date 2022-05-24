package com.qq.oauth2.resouce.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true,securedEnabled = true)
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("res1")
//                .tokenServices(remoteTokenServices());
                .tokenServices(tokenServices());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**")
                // 认证中心配置的scope
                .access("#oauth2.hasScope('all')")
                .anyRequest()
                .authenticated();
    }

    /**
     * 由于认证中心使用的令牌存储策略是在内存中的，因此服务端必须远程调用认证中心的校验令牌端点"/oauth/check_token"进行校验。
     * 远程校验令牌存在性能问题，但是后续使用JWT令牌则本地即可进行校验，不必远程校验了
     * @return
     */
    public RemoteTokenServices remoteTokenServices(){
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8844/oauth/check_token");
        // 客户端唯一id
        tokenServices.setClientId("qq");
        // 客户端唯一秘钥
        tokenServices.setClientSecret("111");
        return tokenServices;
    }

    /**
     * 生成的ResourceServerTokenServices对象，其中使用JWT令牌增强
     * @return
     */
    @Bean
    public ResourceServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(tokenStore);
        services.setTokenEnhancer(jwtAccessTokenConverter);
        return services;
    }
}
