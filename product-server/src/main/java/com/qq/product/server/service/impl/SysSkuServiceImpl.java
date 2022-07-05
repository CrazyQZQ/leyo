package com.qq.product.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.es.service.EsService;
import com.qq.common.system.pojo.SysOrderDetail;
import com.qq.common.system.pojo.SysProduct;
import com.qq.common.system.pojo.SysSku;
import com.qq.common.system.service.MinIoService;
import com.qq.common.system.vo.SkuAttributeVO;
import com.qq.product.server.constants.ProductConstants;
import com.qq.product.server.mapper.SysAttributeValueMapper;
import com.qq.product.server.mapper.SysSkuMapper;
import com.qq.product.server.service.SysSkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final EsService esService;
    private final SysAttributeValueMapper sysAttributeValueMapper;

    /**
     * 根据商品id查询
     * @param productId
     * @return
     */
    @Override
    public List<SysSku> list(Long productId) {
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setParentId(productId);
        List<SysSku> skuList = this.baseMapper.list(baseQuery);
        for (SysSku sku : skuList) {
            setAttributes(sku);
        }
        return skuList;
    }

    /**
     *  根据id集合查询
     * @param skuIds
     * @return
     */
    @Override
    public List<SysSku> list(List<Long> skuIds) {
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setIds(skuIds);
        List<SysSku> skuList = this.baseMapper.list(baseQuery);
        for (SysSku sku : skuList) {
            setAttributes(sku);
        }
        return skuList;
    }

    /**
     * 核减库存
     * @param id
     * @param stock
     */
    @Override
    public void reduceStock(Long id, Integer stock) throws IOException {
        SysSku sysSku = this.baseMapper.getById(id);
        if(ObjectUtil.isEmpty(sysSku)){
            throw new RuntimeException("商品sku不存在");
        }
        if(sysSku.getStock() < stock){
            throw new RuntimeException("商品库存不足");
        }
        sysSku.setId(id);
        // 减库存
        sysSku.setStock(sysSku.getStock() - stock);
        // 加销量
        sysSku.setSales(sysSku.getSales() + stock);
        this.baseMapper.updateById(sysSku);
        esService.updateDoc(ProductConstants.SKU_INDEX, id.toString(), sysSku);
    }

    /**
     * 上传图片
     * @param id
     * @param file
     */
    @Override
    public void saveImage(Long id, MultipartFile file) throws IOException {
        SysSku sysSku = this.baseMapper.getById(id);
        if(ObjectUtil.isEmpty(sysSku)){
            throw new ServiceException("商品sku不存在");
        }
        String imageUrl = sysSku.getImageUrl();
        if(StrUtil.isNotEmpty(imageUrl)){
            minIoService.deleteFileByFullPath(Arrays.asList(imageUrl));
        }
        String newImageUrl = minIoService.upload(file);
        sysSku.setId(id);
        sysSku.setImageUrl(newImageUrl);
        this.baseMapper.updateById(sysSku);
        esService.updateDoc(ProductConstants.SKU_INDEX, id.toString(), sysSku);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public SysSku getSkuById(Long id) {
        SysSku sku = this.baseMapper.getById(id);
        setAttributes(sku);
        return sku;
    }

    /**
     * 修改es数据
     * @param skuIds
     * @throws IOException
     */
    @Override
    public void updateSkuInEs(List<Long> skuIds) throws IOException {
        List<SysSku> skuList = list(skuIds);
        for (SysSku sku : skuList) {
            esService.updateDoc(ProductConstants.SKU_INDEX, sku.getId().toString(), sku);
        }
    }

    /**
     * 设置sku属性
     * @param sku
     */
    private void setAttributes(SysSku sku){
        if (StrUtil.isNotEmpty(sku.getSpec())) {
            List<Map> specs = JSON.parseArray(sku.getSpec(), Map.class);
            if(CollUtil.isNotEmpty(specs)){
                List<SkuAttributeVO> skuAttributeVOS = sysAttributeValueMapper.selectSkuAttribute(specs);
                sku.setSkuAttributes(skuAttributeVOS);
                String skuAttributes = skuAttributeVOS.stream().map(SkuAttributeVO::getValue).collect(Collectors.joining(","));
                sku.setSkuAttributeStr(skuAttributes);
            }
        }
    }

}




