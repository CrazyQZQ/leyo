package com.qq.order.server.service;

import com.qq.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@Service
@FeignClient(name = "account-server")
public interface AccountService {

    @PostMapping("/operateAccountAmount")
    AjaxResult operateAccountAmount(Long id, BigDecimal amount);

}
