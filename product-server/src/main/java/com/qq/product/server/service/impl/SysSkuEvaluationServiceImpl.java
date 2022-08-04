package com.qq.product.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.system.pojo.SysSkuEvaluation;
import com.qq.product.server.service.SysSkuEvaluationService;
import com.qq.product.server.mapper.SysSkuEvaluationMapper;
import com.qq.product.server.service.SysSkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_sku_evaluation(评价表)】的数据库操作Service实现
* @createDate 2022-08-04 14:37:18
*/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysSkuEvaluationServiceImpl extends ServiceImpl<SysSkuEvaluationMapper, SysSkuEvaluation>
    implements SysSkuEvaluationService{

    private final SysSkuService skuService;

    /**
     * 查询用户商品评价
     * @param userId
     * @param orderId
     * @param orderDetailId
     * @param skuId
     * @return
     */
    @Override
    public List<SysSkuEvaluation> list(Long userId, Long orderId, Long orderDetailId, Long skuId) {
        List<SysSkuEvaluation> list = this.baseMapper.list(userId, orderId, orderDetailId, skuId);
        for (SysSkuEvaluation skuEvaluation: list){
            skuService.setAttributes(skuEvaluation.getSku());
        }
        return list;
    }
}




