package com.qq.product.server.service;

import com.qq.common.system.pojo.SysSkuEvaluation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_sku_evaluation(评价表)】的数据库操作Service
* @createDate 2022-08-04 14:37:18
*/
public interface SysSkuEvaluationService extends IService<SysSkuEvaluation> {

    /**
     * 查询用户商品评价
     * @param userId
     * @param orderId
     * @param orderDetailId
     * @param skuId
     * @return
     */
    List<SysSkuEvaluation> list(Long userId, Long orderId, Long orderDetailId, Long skuId);
}
