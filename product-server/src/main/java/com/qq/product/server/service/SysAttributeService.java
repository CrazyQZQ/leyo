package com.qq.product.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.system.pojo.SysAttribute;

/**
* @author Administrator
* @description 针对表【sys_attribute(属性表)】的数据库操作Service
* @createDate 2022-05-23 17:08:20
*/
public interface SysAttributeService extends IService<SysAttribute> {

    void add(SysAttribute attribute);

    void update(SysAttribute attribute);

    void delete(Long id);
}
