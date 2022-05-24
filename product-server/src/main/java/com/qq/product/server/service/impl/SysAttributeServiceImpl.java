package com.qq.product.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.core.exception.ServiceException;
import com.qq.common.system.pojo.SysAttribute;
import com.qq.common.system.pojo.SysAttributeValue;
import com.qq.product.server.mapper.SysAttributeMapper;
import com.qq.product.server.mapper.SysAttributeValueMapper;
import com.qq.product.server.service.SysAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author Administrator
* @description 针对表【sys_attribute(属性表)】的数据库操作Service实现
* @createDate 2022-05-23 17:08:20
*/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysAttributeServiceImpl extends ServiceImpl<SysAttributeMapper, SysAttribute>
    implements SysAttributeService {

    private final SysAttributeValueMapper attributeValueMapper;

    @Override
    @Transactional
    public void add(SysAttribute attribute) {
        this.baseMapper.insert(attribute);
        // 插入属性值
        if(CollUtil.isNotEmpty(attribute.getValues())){
            for (SysAttributeValue value : attribute.getValues()) {
                value.setAttributeId(attribute.getId());
                attributeValueMapper.insert(value);
            }
        }
    }

    @Override
    @Transactional
    public void update(SysAttribute attribute) {
        this.baseMapper.updateById(attribute);
        // 更新属性值
        if(CollUtil.isNotEmpty(attribute.getValues())){
            for (SysAttributeValue value : attribute.getValues()) {
                if(value.getId() == null){
                    value.setAttributeId(attribute.getId());
                    attributeValueMapper.insert(value);
                }else{
                    attributeValueMapper.updateById(value);
                }
            }
        }
    }

    @Override
    public void delete(Long id) {
        Integer count = attributeValueMapper.selectCount(new QueryWrapper<SysAttributeValue>().eq("attribute_id", id));
        if(count > 0){
            throw new ServiceException("该属性下存在属性值，不能删除");
        }
        this.baseMapper.deleteById(id);
    }
}




