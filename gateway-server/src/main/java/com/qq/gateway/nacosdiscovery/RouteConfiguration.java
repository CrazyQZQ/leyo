package com.qq.gateway.nacosdiscovery;

import org.springframework.context.annotation.Configuration;

/**
 * @Description: 路由配置
 * @Author QinQiang
 * @Date 2022/4/6
 **/
@Configuration
public class RouteConfiguration {
    // 配置路由
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes().route(r ->
//                r.path("/demo/**")
//                        .uri("lb://demo-provider ")
//                        .filter(new TokenGatewayFilter())
//                        .id("demo_route "))
//                .build();
//    }
}
