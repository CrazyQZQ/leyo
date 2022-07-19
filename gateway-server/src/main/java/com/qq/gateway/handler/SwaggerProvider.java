package com.qq.gateway.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: swagger聚合系统接口
 * @Author QinQiang
 * @Date 2022/7/19
 **/
@Component
@Primary
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SwaggerProvider implements SwaggerResourcesProvider {

    // SWAGGER3默认的URL后缀
    public static final String API_URI = "/v3/api-docs";
    // 网关路由
    private final RouteLocator routeLocator;

    private final GatewayProperties gatewayProperties;

    /**
     * 这个类是核心，这个类封装的是SwaggerResource，即在swagger-ui.html页面中顶部的选择框，选择服务的swagger页面内容。
     * RouteLocator：获取spring cloud gateway中注册的路由
     * RouteDefinitionLocator：获取spring cloud gateway路由的详细信息
     * RestTemplate：获取各个配置有swagger的服务的swagger-resources
     */
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        //取出gateway的route
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        //结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
                                predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                        .replace("/**", API_URI)))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("3.0.0");
        return swaggerResource;
    }
}
