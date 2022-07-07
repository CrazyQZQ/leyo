package com.qq.order.server.vo;

import com.qq.common.system.pojo.SysOrder;
import com.qq.common.system.pojo.SysOrderDetail;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/13
 **/
@Data
public class OrderVO {

    /**
     * 主表数据
     */
    private SysOrder order;

    /**
     * 子表数据
     */
    private List<SysOrderDetail> orderDetailList;

    /**
     * 账户id
     */
    private Long accountId;
}
