package com.qq.gateway_demo.handler;

import com.alibaba.fastjson.JSON;
import com.qq.common.core.web.domain.AjaxResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @Description: 在JwtAccessManager，如果无该权限，也是会直接返回，这里也需要定制提示信息。
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@Component
public class RequestAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(new AjaxResult(401, "无权限访问")).getBytes(Charset.forName("utf-8")));
        return response.writeWith(Mono.just(buffer));
    }
}
