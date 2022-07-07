package com.qq.product.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.es.service.EsService;
import com.qq.common.system.mapper.SysObjectImagesMapper;
import com.qq.common.system.pojo.*;
import com.qq.common.system.service.MinIoService;
import com.qq.common.system.utils.OauthUtils;
import com.qq.product.server.constants.ProductConstants;
import com.qq.product.server.mapper.SysAttributeValueMapper;
import com.qq.product.server.mapper.SysProductAttributeMapper;
import com.qq.product.server.mapper.SysProductMapper;
import com.qq.product.server.service.SysProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_product(商品表)】的数据库操作Service实现
 * @createDate 2022-05-06 16:44:17
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysProductServiceImpl extends ServiceImpl<SysProductMapper, SysProduct>
        implements SysProductService {

    private final MinIoService minIoService;
    private final SysObjectImagesMapper sysObjectImagesMapper;
    private final SysProductAttributeMapper productAttributeMapper;
    private final EsService esService;
    private final SysAttributeValueMapper sysAttributeValueMapper;

    /**
     * 新增商品
     *
     * @param sysProduct 商品信息
     */
    @Override
    @Transactional
    public void addProduct(SysProduct sysProduct) {
        sysProduct.setCreateBy(OauthUtils.getCurrentUserName());
        sysProduct.setCreateTime(new Date());
        this.baseMapper.insert(sysProduct);
        MultipartFile[] images = sysProduct.getImages();
        if (images != null && images.length > 0) {
            for (MultipartFile image : images) {
                String upload = minIoService.upload(image);
                SysObjectImages sysObjectImages = new SysObjectImages();
                sysObjectImages.setObjectId(sysProduct.getId());
                sysObjectImages.setImageUrl(upload);
                sysObjectImages.setObjectType(3);
                sysObjectImagesMapper.insert(sysObjectImages);
            }
        }
        // 保存属性
        List<SysAttribute> attributes = sysProduct.getAttributes();
        if (CollUtil.isNotEmpty(attributes)) {
            for (SysAttribute attribute : attributes) {
                SysProductAttribute sysProductAttribute = new SysProductAttribute();
                sysProductAttribute.setProductId(sysProduct.getId());
                sysProductAttribute.setAttributeId(attribute.getId());
                productAttributeMapper.insert(sysProductAttribute);
            }
        }
    }

    /**
     * @param id
     * @return
     * @description 根据商品id查询商品
     */
    @Override
    public SysProduct getProductById(Long id) {
        SysProduct product = this.baseMapper.getProductById(id);
        if (ObjectUtil.isNotEmpty(product)) {
            List<SysSku> skus = product.getSkus();
            for (SysSku sku : skus) {
                if (StrUtil.isNotEmpty(sku.getSpec())) {
                    List<Map> specs = JSON.parseArray(sku.getSpec(), Map.class);
                    if (CollUtil.isNotEmpty(specs)) {
                        sku.setSkuAttributes(sysAttributeValueMapper.selectSkuAttribute(specs));
                    }
                }
            }
        }
        return product;
    }

    /**
     * @return
     * @description 分页查询商品
     */
    @Override
    public List<SysProduct> getProductList(BaseQuery query) {
        return this.baseMapper.getProductList(query);
    }

    /**
     * @param product
     * @return
     * @description 更新商品
     */
    @Override
    @Transactional
    public int updateProduct(SysProduct product) throws IOException {
        if (product.getId() == null) {
            throw new ServiceException("商品id不能为空！");
        }
        product.setUpdateBy(OauthUtils.getCurrentUserName());
        product.setUpdateTime(new Date());
        int i = this.baseMapper.updateById(product);
        if (i == 0) {
            throw new ServiceException("商品不存在！");
        }
        List<SysAttribute> attributes = product.getAttributes();
        if (CollUtil.isNotEmpty(attributes)) {
            productAttributeMapper.delete(new QueryWrapper<SysProductAttribute>().eq("product_id", product.getId()));
            for (SysAttribute attribute : attributes) {
                SysProductAttribute sysProductAttribute = new SysProductAttribute();
                sysProductAttribute.setProductId(product.getId());
                sysProductAttribute.setAttributeId(attribute.getId());
                productAttributeMapper.insert(sysProductAttribute);
            }
        }
        esService.updateDoc(ProductConstants.PRODUCT_INDEX, product.getId().toString(),
                this.baseMapper.selectMaps(new QueryWrapper<SysProduct>().eq("id", product.getId())).get(0));
        return i;
    }

    /**
     * @param id
     * @return
     * @description 删除商品
     */
    @Override
    @Transactional
    public int deleteProduct(Long id) throws IOException {
        List<String> images = sysObjectImagesMapper.selectList(new QueryWrapper<SysObjectImages>().eq("product_id", id))
                .stream().map(SysObjectImages::getImageUrl)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(images)) {
            sysObjectImagesMapper.delete(new QueryWrapper<SysObjectImages>().eq("object_id", id));
            minIoService.deleteFileByFullPath(images);
        }
        esService.deleteDoc(ProductConstants.PRODUCT_INDEX, id.toString());
        productAttributeMapper.delete(new QueryWrapper<SysProductAttribute>().eq("product_id", id));
        return this.baseMapper.deleteById(id);
    }
}




