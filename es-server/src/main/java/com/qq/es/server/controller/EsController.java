package com.qq.es.server.controller;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/10
 **/

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.es.service.EsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("search")
@Slf4j
@AllArgsConstructor
public class EsController {
    private final EsService esService;

    @RequestMapping("list")
    public AjaxResult list(String keyword, Integer indexName, Integer pageSize) {

        return AjaxResult.success();
    }

}
