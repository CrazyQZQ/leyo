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
public class ProductVO {

    private SysOrder order;

    private List<SysOrderDetail> orderDetailList;

    private Long accountId;
}
