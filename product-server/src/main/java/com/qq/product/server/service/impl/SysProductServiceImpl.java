package com.qq.product.server.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.JacksonUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.utils.bean.BeanUtils;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.es.service.EsService;
import com.qq.common.system.service.MinIoService;
import com.qq.common.system.utils.OauthUtils;
import com.qq.product.server.mapper.SysProductBrandMapper;
import com.qq.product.server.mapper.SysProductImagesMapper;
import com.qq.product.server.mapper.SysProductMapper;
import com.qq.product.server.pojo.SysProduct;
import com.qq.product.server.pojo.SysProductBrand;
import com.qq.product.server.pojo.SysProductImages;
import com.qq.product.server.service.SysProductService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_product(商品表)】的数据库操作Service实现
 * @createDate 2022-05-06 16:44:17
 */
@Service
@AllArgsConstructor
public class SysProductServiceImpl extends ServiceImpl<SysProductMapper, SysProduct>
        implements SysProductService {

    private final MinIoService minIoService;
    private final SysProductImagesMapper sysProductImagesMapper;
    private final EsService esService;
    private final SysProductBrandMapper sysProductBrandMapper;

    @Override
    @Transactional
    public void addProduct(SysProduct sysProduct) throws IOException {
        sysProduct.setCreateBy(OauthUtils.getCurrentUserName());
        sysProduct.setCreateTime(new Date());
        this.baseMapper.insert(sysProduct);
        MultipartFile[] images = sysProduct.getImages();
        if (images != null && images.length > 0) {
            for (MultipartFile image : images) {
                String upload = minIoService.upload(image);
                SysProductImages sysProductImages = new SysProductImages();
                sysProductImages.setProductId(sysProduct.getId());
                sysProductImages.setImageUrl(upload);
                sysProductImagesMapper.insert(sysProductImages);
            }
        }
        if(ObjectUtils.isNotEmpty(sysProduct.getBrandId())){
            SysProductBrand productBrand = new SysProductBrand();
            productBrand.setBrandId(productBrand.getProductId());
            productBrand.setProductId(sysProduct.getId());
            sysProductBrandMapper.insert(productBrand);
        }
        esService.addDoc("product", sysProduct.getId().toString(),
                this.baseMapper.selectMaps(new QueryWrapper<SysProduct>().eq("id", sysProduct.getId())).get(0));
    }

    @Override
    public SysProduct getProductById(Long id) {
        return this.baseMapper.getProductById(id);
    }

    @Override
    public List<SysProduct> getProductList(BaseQuery query) {
        return this.baseMapper.getProductList(query);
    }

    @Override
    @Transactional
    public int updateProduct(SysProduct product) throws IOException {
        if(product.getId() == null){
            throw new ServiceException("商品id不能为空！");
        }
        product.setUpdateBy(OauthUtils.getCurrentUserName());
        product.setUpdateTime(new Date());
        int i = this.baseMapper.updateById(product);
        if(i == 0){
            throw new ServiceException("商品不存在！");
        }
        if(ObjectUtils.isNotEmpty(product.getBrandId())){
            SysProductBrand productBrand = new SysProductBrand();
            productBrand.setBrandId(product.getBrandId());
            int rows = sysProductBrandMapper.update(productBrand, new UpdateWrapper<SysProductBrand>().eq("product_id", product.getId()));
            if(rows < 1){
                productBrand.setProductId(product.getId());
                sysProductBrandMapper.insert(productBrand);
            }
        }
        esService.updateDoc("product", product.getId().toString(),
                this.baseMapper.selectMaps(new QueryWrapper<SysProduct>().eq("id", product.getId())).get(0));
        return i;
    }

    @Override
    @Transactional
    public int deleteProduct(Long id) throws IOException {
        esService.deleteDoc("product", id.toString());
        List<String> images = sysProductImagesMapper.selectList(new QueryWrapper<SysProductImages>().eq("product_id", id))
                .stream().map(SysProductImages::getImageUrl)
                .collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(images)){
            sysProductImagesMapper.delete(new QueryWrapper<SysProductImages>().eq("product_id", id));
            minIoService.deleteFile(images);
        }
        sysProductBrandMapper.delete(new QueryWrapper<SysProductBrand>().eq("product_id", id));
        return this.baseMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void reduceStock(Long id, Integer stock) throws IOException {
        SysProduct product = this.baseMapper.getProductById(id);
        if(product == null){
            throw new ServiceException("商品不存在！");
        }
        if(stock > product.getStock()){
            throw new ServiceException("库存不足！");
        }
        product.setStock(product.getStock() - stock);
        this.baseMapper.updateById(product);
        esService.updateDoc("product", product.getId().toString(),
                this.baseMapper.selectMaps(new QueryWrapper<SysProduct>().eq("id", product.getId())).get(0));
    }
}




