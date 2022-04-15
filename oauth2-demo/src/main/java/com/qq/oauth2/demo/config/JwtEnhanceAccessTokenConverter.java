package com.qq.oauth2.demo.config;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

/**
 * @author 公众号：码猿技术专栏
 * 令牌转换器，将身份认证信息转换后存储在令牌内
 */
public class JwtEnhanceAccessTokenConverter extends DefaultAccessTokenConverter {
    public JwtEnhanceAccessTokenConverter(){
        super.setUserTokenConverter(new JwtEnhanceUserAuthenticationConverter());
    }
}
