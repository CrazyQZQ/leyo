package com.qq.es.server.controller;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/10
 **/

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.es.service.EsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("search")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EsController {
    private final EsService esService;

    @RequestMapping("list")
    public AjaxResult list(String keyword, Integer indexName, Integer pageSize) {

        return AjaxResult.success();
    }

}
