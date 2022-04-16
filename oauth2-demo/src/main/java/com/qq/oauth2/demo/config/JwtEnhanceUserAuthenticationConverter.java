package com.qq.oauth2.demo.config;

import com.qq.common.core.constant.TokenConstants;
import com.qq.common.system.pojo.SysUser;
import com.qq.oauth2.demo.pojo.SecurityUser;
import com.qq.oauth2.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: JWT用户认证转换器
 * @Author QinQiang
 * @Date 2022/4/12
 **/
public class JwtEnhanceUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    public JwtEnhanceUserAuthenticationConverter(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }
    /**
     * 重写抽取用户数据方法,获取token时把用户信息放入token中
     */
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            String username = (String) map.get(USERNAME);
            String userId = map.get(TokenConstants.USER_ID).toString();
            SecurityUser user =new SecurityUser();
            user.setUserId(Long.valueOf(com.qq.common.core.utils.StringUtils.nvl(userId,"-1")));
            user.setUsername(username);
            return new UsernamePasswordAuthenticationToken(user, "", authorities);
        }
        return null;
    }

    /**
     * 刷新token时时把用户信息放入token中
     * @param authentication
     * @return
     */
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        response.put("user_id", user.getUserId());
        response.put("user_name", authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

    /**
     * 提取权限
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }

}