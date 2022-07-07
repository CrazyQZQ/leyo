package com.qq.product.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.common.core.web.page.BaseQuery;
import com.qq.common.system.pojo.SysBrand;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【sys_brand(商品品牌表)】的数据库操作Mapper
 * @createDate 2022-05-11 10:03:37
 * @Entity com.qq.common.system.pojo.SysBrand
 */
public interface SysBrandMapper extends BaseMapper<SysBrand> {

    List<SysBrand> getBrandList(BaseQuery query);
}




