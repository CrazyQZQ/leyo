package com.qq.product.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.system.pojo.SysAttribute;

/**
 * @author Administrator
 * @description 针对表【sys_attribute(属性表)】的数据库操作Service
 * @createDate 2022-05-23 17:08:20
 */
public interface SysAttributeService extends IService<SysAttribute> {

    /**
     * 新增属性
     *
     * @param attribute
     */
    void add(SysAttribute attribute);

    /**
     * 修改属性
     *
     * @param attribute
     */
    void update(SysAttribute attribute);

    /**
     * 删除属性
     *
     * @param id
     */
    void delete(Long id);
}
