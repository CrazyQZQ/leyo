package com.qq.oauth2.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.common.system.mapper.SysUserMapper;
import com.qq.common.system.pojo.SysRole;
import com.qq.common.system.pojo.SysUser;
import com.qq.common.system.service.SysRoleService;
import com.qq.oauth2.server.pojo.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/8
 **/
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", username);
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        List<SysRole> roles = sysRoleService.getByUser(sysUser.getUserId());
        for (SysRole role : roles) {
            // 角色必须是ROLE_开头，可以在数据库中设置
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getRoleKey());
            grantedAuthorities.add(grantedAuthority);

            // 获取权限
//            Wrapper<List<MenuVo>> menuInfo = userProvider.getRolePermission(String.valueOf(role.getId()));
//            if (menuInfo.getCode() == Constants.SUCCESS) {
//                List<MenuVo> permissionList = menuInfo.getResult();
//                for (MenuVo menu : permissionList) {
//                    if (!StringUtils.isEmpty(menu.getUrl())) {
//                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(menu.getUrl());
//                        grantedAuthorities.add(authority);
//                    }
//                }
//            }
        }

//        AuthUser user = new AuthUser(sysUser.getUserName(), sysUser.getPassword(), grantedAuthorities);
//        user.setId(sysUser.getUserId());
//        return user;
        return new SecurityUser(sysUser.getUserId(),sysUser.getUserName(), sysUser.getPassword(), grantedAuthorities);
    }
}
