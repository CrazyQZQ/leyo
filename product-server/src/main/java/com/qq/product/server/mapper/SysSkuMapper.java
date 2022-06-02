package com.qq.product.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.common.system.pojo.SysSku;

/**
* @author Administrator
* @description 针对表【sys_sku(商品sku表)】的数据库操作Mapper
* @createDate 2022-05-23 17:08:20
* @Entity com.qq.common.system.pojo.SysSku
*/
public interface SysSkuMapper extends BaseMapper<SysSku> {

    SysSku getById(Long id);
}




