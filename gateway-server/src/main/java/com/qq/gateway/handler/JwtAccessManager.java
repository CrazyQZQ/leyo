package com.qq.gateway.handler;

import com.qq.common.core.constant.CacheConstants;
import com.qq.common.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * @Description: 鉴权管理器
 * 经过认证管理器JwtAuthenticationManager认证成功后，就需要对令牌进行鉴权，如果该令牌无访问资源的权限，则不允通过。
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAccessManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final RedisService redisService;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        // 这里是直接从Redis中取出资源URI对应的权限集合，因此实际开发中需要维护资源URI和权限的对应关系，
        // 直接在项目启动的时候向Redis中添加了两个资源的权限，
        List<String> authorities = redisService.getCacheList(CacheConstants.MENU_ROLES + uri.getPath());

        return mono.filter(Authentication::isAuthenticated)
                // 这处代码就是取出令牌中的权限集合
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                // 这处代码就是比较两者权限了，有交集，则放行
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
