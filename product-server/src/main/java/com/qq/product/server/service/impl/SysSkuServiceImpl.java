package com.qq.product.server.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.system.pojo.SysSku;
import com.qq.product.server.mapper.SysSkuMapper;
import com.qq.product.server.service.SysSkuService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_sku(商品sku表)】的数据库操作Service实现
* @createDate 2022-05-23 17:08:20
*/
@Service
public class SysSkuServiceImpl extends ServiceImpl<SysSkuMapper, SysSku>
    implements SysSkuService {

    @Override
    public void reduceStock(Long id, Integer stock) {
        SysSku sysSku = this.baseMapper.selectById(id);
        if(ObjectUtil.isEmpty(sysSku)){
            throw new RuntimeException("商品sku不存在");
        }
        if(sysSku.getStock() < stock){
            throw new RuntimeException("商品库存不足");
        }
        SysSku sku = new SysSku();
        sku.setId(id);
        sku.setStock(sysSku.getStock() - stock);
        this.baseMapper.updateById(sku);
    }
}




