package com.qq.product.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.common.system.pojo.SysOrderDetail;
import com.qq.common.system.pojo.SysSku;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【sys_sku(商品sku表)】的数据库操作Service
 * @createDate 2022-05-23 17:08:20
 */
public interface SysSkuService extends IService<SysSku> {

    /**
     * 根据商品id查询
     *
     * @param productId
     * @return
     */
    List<SysSku> list(Long productId);

    /**
     * 根据id集合查询
     *
     * @param skuIds
     * @return
     */
    List<SysSku> list(List<Long> skuIds);

    /**
     * 核减库存
     *
     * @param id
     * @param stock
     */
    void reduceStock(Long id, Integer stock) throws IOException;

    /**
     * 上传图片
     *
     * @param id
     * @param file
     */
    void saveImage(Long id, MultipartFile file) throws IOException;

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    SysSku getSkuById(Long id);

    /**
     * 修改es数据
     *
     * @param skuIds
     * @throws IOException
     */
    void updateSkuInEs(List<Long> skuIds) throws IOException;

    /**
     * 设置sku属性
     *
     * @param sku
     */
    void setAttributes(SysSku sku);
}
