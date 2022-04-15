package com.qq.gateway_demo.config;

import com.qq.common.core.constant.TokenConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Description: token存储策略
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@Configuration
public class AccessTokenConfig {

    /**
     * 令牌的存储策略，这里使用的是JwtTokenStore，使用JWT的令牌生成方式，其实还有以下两个比较常用的方式：
     *
     * RedisTokenStore：将令牌存储到Redis中，此种方式相对于内存方式来说性能更好
     * JdbcTokenStore：将令牌存储到数据库中，需要新建从对应的表，有兴趣的可以尝试
     * @return
     */
    @Bean
    public TokenStore tokenStore(){

//        return new InMemoryTokenStore();
        // 使用jwt
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 令牌增强类，用于JWT令牌和OAuth身份进行转换
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(TokenConstants.SIGN_KEY);
        return tokenConverter;
    }
}
