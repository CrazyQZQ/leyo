package com.qq.product.server.controller;

import com.qq.common.es.service.EsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 商品信息查询
 * @Author QinQiang
 * @Date 2022/5/9
 **/
@RestController
@RequestMapping("product/search")
@Slf4j
@AllArgsConstructor
public class SysProductSearchController {
    private final EsService esService;


}
