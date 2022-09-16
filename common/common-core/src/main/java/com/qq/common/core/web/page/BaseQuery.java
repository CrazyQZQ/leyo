package com.qq.common.core.web.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/12
 **/
@Data
@ApiModel("基本查询条件")
public class BaseQuery {
    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("主键")
    private List<Long> ids;
    @ApiModelProperty("关键字")
    private String keyword;
    @ApiModelProperty("父级ID")
    private Long parentId;
    @ApiModelProperty("用户ID")
    private Long userId;
}
