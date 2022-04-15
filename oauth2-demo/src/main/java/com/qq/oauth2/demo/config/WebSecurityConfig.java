package com.qq.oauth2.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
        System.out.println("pwd:"+bCryptPasswordEncoder.encode("123"));
        return bCryptPasswordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;


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
        // 登录
        http.authorizeRequests().anyRequest().authenticated().and()
                .formLogin()
                 // 登录页
//                .loginPage("/login.html")
                // 登录提交url
//                .loginProcessingUrl("/doLogin")
//                .usernameParameter("username")// 用户名key
//                .passwordParameter("password")// 密码key
//                .successForwardUrl("/index")// 登录成功重定向的url
//                .failureForwardUrl("/error")// 登录失败重定向的url
                .permitAll()
//                .and() // 配置注销
//                .logout()
//                .logoutUrl("/logout")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))
//                .logoutSuccessUrl("/index")
//                .deleteCookies()
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .permitAll()
//                .and()
//                .requestMatchers()//系统中所有请求
//                .antMatchers("/**")//SpringSecurity接管的请求/**系统所有请求
//                .and()
//                .authorizeRequests()//得到SpringSecurity接管的请求
//                .antMatchers("/test/*")//给接管的请求(/**)中的/test/*
////                .hasAnyAuthority("p1")//配置需要p1权限
//                .hasAnyRole("admin")// 配置需要ADMIN角色
//                .antMatchers("/user/*")//给接管的请求(/**)中的/mbb/*
////                .permitAll()
//                .hasAnyRole("user")
////                .hasAnyAuthority("p1")
//                .anyRequest()//其他请求
//                .authenticated()//都需要认证
                .and()
                .csrf().disable()//关闭csrf
        ;
    }


}
