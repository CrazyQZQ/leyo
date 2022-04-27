package com.qq.oauth2.server.config;

import com.qq.common_redis.service.RedisService;
import com.qq.oauth2.server.handler.FebsWebLoginFailureHandler;
import com.qq.oauth2.server.handler.FebsWebLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description: SpringSecurity核心配置
 * @Author QinQiang
 * @Date 2022/4/12
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 密码加密
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("pwd:" + bCryptPasswordEncoder.encode("123"));
        return bCryptPasswordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public RedisService redisService() {
        return new RedisService();
    }

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Autowired
    private FebsWebLoginSuccessHandler successHandler;
    @Autowired
    private FebsWebLoginFailureHandler failureHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 这里配置全局用户信息
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(passwordEncoder().encode("123"))
//                .roles("admin")
////                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("p1,p2"))
//                .and()
//                .withUser("user")
//                .password(passwordEncoder().encode("123"))
//                .roles("user");
//                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("p1,p2"));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 如果只配置loginPage而不配置loginProcessingUrl的话
        // 那么loginProcessingUrl默认就是loginPage
        // 你配置的loginPage("/testpage.html") ,那么loginProcessingUrl就是"/testpage.html"
        http
                //注入自定义的授权配置类
                // .apply(smsCodeSecurityConfig)
                // .and()
                .authorizeRequests()
                .antMatchers( "/register", "/captchaImage").anonymous()
                //注销的接口需要放行
                .antMatchers("/oauth/**",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/profile/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/oauth/login")
                // loginProcessingUrl的作用是用来拦截前端页面对/login/doLogin这个的请求的，拦截到了就走它自己的处理流程（例如这个UserDetailsService的loadUserByUsername这个方法
                .loginProcessingUrl("/oauth/doLogin")
                // .successForwardUrl("/oauth/index")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/oauth/logout")
                .permitAll()
                .and()
                .csrf()
                .disable();

    }


}
