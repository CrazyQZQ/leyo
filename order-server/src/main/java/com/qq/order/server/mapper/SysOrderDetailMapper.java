package com.qq.order.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.common.system.pojo.SysOrderDetail;
import com.qq.common.system.pojo.SysSku;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【sys_order_detail(订单详情表)】的数据库操作Mapper
 * @createDate 2022-05-06 16:44:17
 * @Entity com.qq.common.system.pojo.SysOrderDetail
 */
public interface SysOrderDetailMapper extends BaseMapper<SysOrderDetail> {

    /**
     * 查询热卖商品
     * @return
     */
    List<Long> selectHostSales();

}




