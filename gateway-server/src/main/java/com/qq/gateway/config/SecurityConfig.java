package com.qq.gateway.config;

import com.qq.gateway.filter.CorsFilter;
import com.qq.gateway.handler.JwtAccessManager;
import com.qq.gateway.handler.RequestAccessDeniedHandler;
import com.qq.gateway.handler.RequestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import java.util.List;


/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {


    // 鉴权管理器
    @Autowired
    private JwtAccessManager jwtAccessManager;
    @Autowired
    private RequestAuthenticationEntryPoint requestAuthenticationEntryPoint;
    @Autowired
    private RequestAccessDeniedHandler requestAccessDeniedHandler;
    @Autowired
    private ReactiveAuthenticationManager jwtAuthenticationManager;
    @Autowired
    private CorsFilter corsFilter;

    /**
     * 系统参数配置
     */
    @Autowired
    private SysParameterConfig sysConfig;

    @Bean
    public SecurityWebFilterChain webFluxFilterChain(ServerHttpSecurity http) {
        AuthenticationWebFilter webFilter = new AuthenticationWebFilter(jwtAuthenticationManager);
        List<String> ignoreUrls = sysConfig.getIgnoreUrls();
        webFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());
        http.
                httpBasic().disable()
                .csrf().disable()
                .authorizeExchange()
                // 白名单
                .pathMatchers(ignoreUrls.toArray(new String[ignoreUrls.size()])).permitAll()
                // 其他请求使用鉴权处理器
                .anyExchange().access(jwtAccessManager)
                .and()
                // 鉴权的异常处理
                .exceptionHandling()
                .authenticationEntryPoint(requestAuthenticationEntryPoint)
                .accessDeniedHandler(requestAccessDeniedHandler)
                .and()
                // 跨域配置
                .addFilterAt(corsFilter, SecurityWebFiltersOrder.CORS)
                // 认证过滤器配置
                .addFilterAt(webFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();

    }
}
