package com.qq.product.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.system.pojo.SysProduct;
import com.qq.common.system.pojo.SysProductCollection;
import com.qq.common.system.utils.OauthUtils;
import com.qq.product.server.mapper.SysProductMapper;
import com.qq.product.server.service.SysProductCollectionService;
import com.qq.product.server.mapper.SysProductCollectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_product_collection(商品收藏表)】的数据库操作Service实现
 * @createDate 2022-08-04 14:37:18
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysProductCollectionServiceImpl extends ServiceImpl<SysProductCollectionMapper, SysProductCollection>
        implements SysProductCollectionService {

    private final SysProductMapper sysProductMapper;

    /**
     * 查询收藏商品
     *
     * @param query
     * @return
     */
    @Override
    public List<SysProduct> listProductCollections(BaseQuery query) {
        if (query.getUserId() == null) {
            throw new ServiceException("用户id不能为空！");
        }
        LambdaQueryWrapper<SysProductCollection> queryWrapper = new LambdaQueryWrapper<SysProductCollection>()
                .eq(SysProductCollection::getUserId, query.getUserId())
                .eq(SysProductCollection::getStatus, 1)
                .orderByDesc(SysProductCollection::getCreateTime);
        List<SysProductCollection> productCollections = this.getBaseMapper().selectList(queryWrapper);
        if (CollUtil.isNotEmpty(productCollections)) {
            List<Long> productIds = productCollections.stream().map(SysProductCollection::getProductId).collect(Collectors.toList());
            query.setIds(productIds);
            return sysProductMapper.getProductList(query);
        }
        return Collections.emptyList();
    }

    @Override
    public void collect(SysProductCollection productCollection) {
        if (productCollection.getUserId() == null) {
            throw new ServiceException("用户id不能为空！");
        }
        if (productCollection.getProductId() == null) {
            throw new ServiceException("商品id不能为空！");
        }
        Integer count = this.getBaseMapper().selectCount(new LambdaQueryWrapper<SysProductCollection>()
                .eq(SysProductCollection::getUserId, productCollection.getUserId())
                .eq(SysProductCollection::getProductId, productCollection.getProductId()));
        productCollection.setStatus(1);
        if(count > 0){
            this.getBaseMapper().update(productCollection, new LambdaQueryWrapper<SysProductCollection>()
                    .eq(SysProductCollection::getUserId, productCollection.getUserId())
                    .eq(SysProductCollection::getProductId, productCollection.getProductId()));
        }else {
            productCollection.setCreateBy(OauthUtils.getCurrentUserName());
            this.getBaseMapper().insert(productCollection);
        }
    }
}




