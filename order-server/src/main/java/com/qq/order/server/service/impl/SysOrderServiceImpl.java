package com.qq.order.server.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.order.server.mapper.SysOrderMapper;
import com.qq.order.server.pojo.SysOrder;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_order(订单表)】的数据库操作Service实现
* @createDate 2022-05-06 16:44:17
*/
@Service
public class SysOrderServiceImpl extends ServiceImpl<SysOrderMapper, SysOrder>
    implements IService<SysOrder> {

}




