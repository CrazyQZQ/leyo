package com.qq.product.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.system.pojo.SysSku;

/**
* @author Administrator
* @description 针对表【sys_sku(商品sku表)】的数据库操作Service
* @createDate 2022-05-23 17:08:20
*/
public interface SysSkuService extends IService<SysSku> {

    void reduceStock(Long id, Integer stock);
}
