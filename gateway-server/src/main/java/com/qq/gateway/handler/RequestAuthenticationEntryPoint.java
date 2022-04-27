package com.qq.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.qq.common.core.enums.AuthResultCode;
import com.qq.common.core.web.domain.AjaxResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @Description: 在JwtAccessManager，如果令牌失效或者过期，则会直接返回，这里需要定制提示信息。
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@Component
public class RequestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(new AjaxResult(AuthResultCode.INVALID_TOKEN.getCode(), AuthResultCode.INVALID_TOKEN.getMsg())).getBytes(Charset.forName("utf-8")));
        return response.writeWith(Mono.just(buffer));
    }
}
