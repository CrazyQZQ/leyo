package com.qq.oauth2.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.qq.common.system.mapper.SysRoleMapper;
import com.qq.common.system.mapper.SysUserMapper;
import com.qq.common.system.pojo.SysRole;
import com.qq.common.system.pojo.SysUser;
import com.qq.oauth2.server.pojo.SecurityUser;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper userMapper;

    private final SysRoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", username);
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        List<SysRole> roles = roleMapper.selectJoinList(SysRole.class, new MPJQueryWrapper<SysRole>()
                .selectAll(SysRole.class)
                .leftJoin("sys_user_role sur on t.role_id = sur.role_id")
                .eq("sur.user_id", sysUser.getUserId()));
        for (SysRole role : roles) {
            // 角色必须是ROLE_开头，可以在数据库中设置
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getRoleKey());
            grantedAuthorities.add(grantedAuthority);
        }

        return new SecurityUser(sysUser.getUserId(), sysUser.getUserName(), sysUser.getPassword(), grantedAuthorities);
    }
}
