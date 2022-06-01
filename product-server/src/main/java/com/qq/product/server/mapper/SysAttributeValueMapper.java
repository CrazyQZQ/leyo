package com.qq.product.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.common.system.pojo.SysAttributeValue;
import com.qq.common.system.vo.SkuAttributeVO;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【sys_attribute_value(商品属性值表)】的数据库操作Mapper
* @createDate 2022-05-23 17:08:20
* @Entity com.qq.common.system.pojo.SysAttributeValue
*/
public interface SysAttributeValueMapper extends BaseMapper<SysAttributeValue> {

    List<SkuAttributeVO> selectSkuAttribute(List<Map> list);

}




