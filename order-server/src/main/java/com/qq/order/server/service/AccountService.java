package com.qq.order.server.service;

import com.qq.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Service
@FeignClient(name = "account-server")
public interface AccountService {

    /**
     * 扣减账户余额
     * @param id
     * @param amount
     * @return
     */
    @PostMapping("/account/operateAccountAmount")
    AjaxResult operateAccountAmount(@RequestParam("id") Long id, @RequestParam("amount") BigDecimal amount);

}
