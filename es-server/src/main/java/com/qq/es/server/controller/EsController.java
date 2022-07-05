package com.qq.es.server.controller;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/5/10
 **/

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.es.service.EsService;
import com.qq.common.es.vo.SearchCommonVO;
import com.qq.common.es.vo.SearchResultVO;
import com.qq.common.log.annotation.Log;
import com.qq.common.system.pojo.SysSku;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<String, Class> SEARCH_INDEX_CLASS_MAP = new HashMap<>();

    {
        SEARCH_INDEX_CLASS_MAP.put("sku", SysSku.class);
    }

    @PostMapping("list")
    @Log(title = "product_sku", funcDesc = "搜索列表")
    public AjaxResult list(@RequestBody SearchCommonVO vo) {
        Class clazz = SEARCH_INDEX_CLASS_MAP.get(vo.getIndexName());
        if (clazz == null) {
            return AjaxResult.error("索引不存在");
        }
        SearchResultVO<Class> search = esService.search(vo, clazz);
        return AjaxResult.success(search);
    }

}
