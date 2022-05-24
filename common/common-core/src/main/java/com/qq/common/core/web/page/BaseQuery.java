package com.qq.common.core.web.page;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/12
 **/
@Data
public class BaseQuery {
    private Date startTime;
    private Date endTime;
    private List<Long> ids;
    private String keyword;
    private Long parentId;
}
