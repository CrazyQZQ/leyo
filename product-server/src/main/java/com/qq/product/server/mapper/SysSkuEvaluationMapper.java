package com.qq.product.server.mapper;

import com.qq.common.system.pojo.SysSkuEvaluation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.common.system.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_sku_evaluation(评价表)】的数据库操作Mapper
* @createDate 2022-08-04 14:37:18
* @Entity com.qq.common.system.pojo.SysSkuEvaluation
*/
public interface SysSkuEvaluationMapper extends BaseMapper<SysSkuEvaluation> {

    List<SysSkuEvaluation> list(@Param("userId") Long userId, @Param("orderId") Long orderId,
                                @Param("orderDetailId") Long orderDetailId, @Param("skuId") Long skuId);
}




