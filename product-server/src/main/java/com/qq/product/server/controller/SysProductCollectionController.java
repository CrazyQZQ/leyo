package com.qq.product.server.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 商品收藏
 * @Author QinQiang
 * @Date 2022/8/4
 **/
@RestController
@RequestMapping("product/collection")
@Slf4j
@Api(tags = "商品收藏")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysProductCollectionController {
}
