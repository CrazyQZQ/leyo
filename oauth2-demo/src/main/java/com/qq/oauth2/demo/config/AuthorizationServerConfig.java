package com.qq.oauth2.demo.config;

import com.qq.oauth2.demo.exception.translator.OAuthServerWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @Description: 授权服核心配置，授权服务需要
 * https://mp.weixin.qq.com/s?__biz=MzU3MDAzNDg1MA==&mid=2247502682&idx=1&sn=52a15b623ab6135c134b8262bd605946&chksm=fcf71497cb809d81f1d2dbce76b3e00170f085306b2a2a67a807a6d9e2cf03bf1de3b8f203a2&token=404376711&lang=zh_CN&scene=21#wechat_redirect
 * @Author QinQiang
 * @Date 2022/4/12
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TokenStore tokenStore;
    @Autowired
    ClientDetailsService clientDetailsService;
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 客户端配置，id，秘钥等
     * <p>
     * 获取授权码：http://localhost:8844/oauth/authorize?client_id=qq&response_type=code&scope=all&redirect_uri=http://localhost:8844/test
     * 如：0qvlYK
     * 获取token http://localhost:8844/oauth/token?code=0qvlYK&client_id=qq&client_secret=111&redirect_uri=http://localhost:8844/test&grant_type=authorization_code
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //基于内存便于测试
        clients.inMemory()// 使用in-memory存储
                .withClient("qq")// client_id
//                .secret("secret")//未加密
                .secret(passwordEncoder.encode("111"))//加密
                .resourceIds("res1")// 资源id列表
                // 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                .scopes("all", "ROLE_ADMIN", "ROLE_USER")// 允许的授权范围
                .autoApprove(false)//false跳转到授权页面
                //加上验证回调地址
                .redirectUris("http://localhost:8844/test");
    }

    /**
     * 令牌断点安全约束配置，如配置/oauth/token对哪些开放
     *
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .passwordEncoder(passwordEncoder)
                // 支持client_id和client_secret做登录验证
                .allowFormAuthenticationForClients();

    }

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * 令牌访问断点配置
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        //配置令牌的访问端点和令牌服务
        //认证管理器
        endpoints
                // 指定异常翻译器
                .exceptionTranslator(new OAuthServerWebResponseExceptionTranslator())
                // 配置了授权码模式所需要的服务，AuthorizationCodeServices
                .authorizationCodeServices(authorizationCodeServices())
                // 配置了密码模式所需要的AuthenticationManager
                .authenticationManager(authenticationManager)
                // 配置了令牌管理服务，AuthorizationServerTokenServices
                .tokenServices(authorizationServerTokenServices())
                // 配置/oauth/token申请令牌的uri只允许POST提交
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)

        ;
    }

    /**
     * 使用授权码模式必须配置一个授权码服务，用来颁布和删除授权码，当然授权码也支持多种方式存储，比如内存，数据库，这里暂时使用内存方式存储
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }


    /**
     * 令牌管理服务配置
     *
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 客户端配置的存储也支持多种方式，比如内存、数据库，对应的接口为
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenStore(tokenStore);
        // 令牌刷新
        tokenServices.setSupportRefreshToken(true);
        // token失效时间
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 2);
        // refreshToken失效时间
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 2);

        // 令牌增强，使用JWT方式生产令牌
        tokenServices.setTokenEnhancer(jwtAccessTokenConverter);
        return tokenServices;
    }
}
