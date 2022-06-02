package com.qq.product.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.system.pojo.SysSku;
import com.qq.common.system.service.MinIoService;
import com.qq.product.server.mapper.SysAttributeValueMapper;
import com.qq.product.server.mapper.SysSkuMapper;
import com.qq.product.server.service.SysSkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【sys_sku(商品sku表)】的数据库操作Service实现
* @createDate 2022-05-23 17:08:20
*/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysSkuServiceImpl extends ServiceImpl<SysSkuMapper, SysSku>
    implements SysSkuService {

    private final MinIoService minIoService;

    private final SysAttributeValueMapper sysAttributeValueMapper;

    @Override
    public List<SysSku> list(Long productId) {
        List<SysSku> skuList = this.baseMapper.selectList(new QueryWrapper<SysSku>().eq("product_id", productId));
        for (SysSku sku : skuList) {
            setAttributes(sku);
        }
        return skuList;
    }

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
        // 减库存
        sku.setStock(sysSku.getStock() - stock);
        // 加销量
        sku.setSales(sysSku.getSales() + stock);
        this.baseMapper.updateById(sku);
    }

    @Override
    public void saveImage(Long id, MultipartFile file) {
        SysSku sysSku = this.baseMapper.selectById(id);
        if(ObjectUtil.isEmpty(sysSku)){
            throw new RuntimeException("商品sku不存在");
        }
        String imageUrl = sysSku.getImageUrl();
        if(StrUtil.isNotEmpty(imageUrl)){
            minIoService.deleteFileByFullPath(Arrays.asList(imageUrl));
        }
        String newImageUrl = minIoService.upload(file);
        SysSku sku = new SysSku();
        sku.setId(id);
        sku.setImageUrl(newImageUrl);
        this.baseMapper.updateById(sku);
    }

    @Override
    public SysSku getSkuById(Long id) {
        SysSku sku = this.baseMapper.selectById(id);
        setAttributes(sku);
        return sku;
    }

    private void setAttributes(SysSku sku){
        if (StrUtil.isNotEmpty(sku.getSpec())) {
            List<Map> specs = JSON.parseArray(sku.getSpec(), Map.class);
            if(CollUtil.isNotEmpty(specs)){
                sku.setSkuAttributes(sysAttributeValueMapper.selectSkuAttribute(specs));
            }
        }
    }
}




