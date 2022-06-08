package com.qq.order.server.pojo;

import com.qq.common.core.web.page.BaseQuery;
import lombok.Data;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/6/8
 **/
@Data
public class OrderQuery extends BaseQuery {
    private Integer status;
    private Long userId;
}
