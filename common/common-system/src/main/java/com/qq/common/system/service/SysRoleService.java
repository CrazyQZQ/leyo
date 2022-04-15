package com.qq.common.system.service;


import com.qq.common.system.pojo.SysRole;

import java.util.List;

public interface SysRoleService {
    List<SysRole> getByUser(Long userId);
}
