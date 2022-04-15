package com.qq.common.system.service.impl;

import com.github.yulichang.query.MPJQueryWrapper;
import com.qq.common.system.mapper.SysRoleMapper;
import com.qq.common.system.pojo.SysRole;
import com.qq.common.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/8
 **/
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;
    @Override
    public List<SysRole> getByUser(Long userId) {
//        leftJoin() 参数说明
//        第一个参数: 参与连表的实体类class
//        第二个参数: 连表的ON字段,这个属性必须是第一个参数实体类的属性
//        第三个参数: 参与连表的ON的另一个实体类属性
//        List<SysRole> sysRoles = roleMapper.selectJoinList(SysRole.class,
//                new MPJLambdaWrapper<SysRole>()
//                        .selectAll(SysRole.class)
//                        .leftJoin(SysUserRole.class, SysUserRole::getRoleId, SysRole::getRoleId)
//                        .eq(SysUserRole::getUserId, userId)
//        );

        List<SysRole> sysRoles = roleMapper.selectJoinList(SysRole.class,new MPJQueryWrapper<SysRole>()
                .selectAll(SysRole.class)
//                .select("addr.tel", "addr.address")
                //行列转换
//                .select("CASE t.sex WHEN '男' THEN '1' ELSE '0' END AS sex")
                //求和函数
//                .select("sum(a.province) AS province")
                //自定义数据集
                .leftJoin("sys_user_role sur on t.role_id = sur.role_id")
                .eq("sur.user_id", userId));
        return sysRoles;
    }
}
