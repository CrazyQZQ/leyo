package com.qq.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.common.system.mapper.SysMessageMapper;
import com.qq.common.system.pojo.SysMessage;
import com.qq.system.service.SysMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/16
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper, SysMessage>
        implements SysMessageService {
}
