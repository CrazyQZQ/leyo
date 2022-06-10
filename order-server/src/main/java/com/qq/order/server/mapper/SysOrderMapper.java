package com.qq.order.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.common.system.pojo.SysOrder;
import com.qq.order.server.pojo.OrderQuery;
import com.qq.order.server.vo.OrderVO;
import com.qq.order.server.vo.StatusCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_order(订单表)】的数据库操作Mapper
* @createDate 2022-05-06 16:44:17
* @Entity com.qq.common.system.pojo.SysOrder
*/
public interface SysOrderMapper extends BaseMapper<SysOrder> {
    List<SysOrder> list(OrderQuery query);

    List<StatusCountVO> getStatusCount(@Param("userId") Long userId);
}




