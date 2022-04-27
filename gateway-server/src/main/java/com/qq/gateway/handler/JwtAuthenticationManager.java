package com.qq.gateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @Description: 认证管理的作用就是获取传递过来的令牌，对其进行解析、验签、过期时间判定。
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@Component
@Slf4j
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private TokenStore tokenStore;

    /**
     * 通过JWT令牌服务解析客户端传递的令牌，并对其进行校验，比如上传三处校验失败，抛出令牌无效的异常。
     * @param authentication
     * @return
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap(accessToken -> {
                    OAuth2AccessToken token = this.tokenStore.readAccessToken(accessToken);
                    if (token == null) {
                        return Mono.error(new InvalidTokenException("无效的token"));
                    } else if (token.isExpired()) {
                        return Mono.error(new InvalidTokenException("token已过期"));
                    }
                    OAuth2Authentication oAuth2Authentication = this.tokenStore.readAuthentication(accessToken);
                    if (oAuth2Authentication == null) {
                        return Mono.error(new InvalidTokenException("无效的token"));
                    } else {
                        return Mono.just(oAuth2Authentication);
                    }
                }).cast(Authentication.class)
                ;
    }
}
