package com.qq.common.core.utils;

import com.github.pagehelper.PageHelper;
import com.qq.common.core.utils.sql.SqlUtil;
import com.qq.common.core.web.page.PageDomain;
import com.qq.common.core.web.page.TableSupport;

import java.util.Optional;

/**
 * 分页工具类
 *
 * @author ruoyi
 */
public class PageUtils extends PageHelper {
    /**
     * 设置请求分页数据
     */
    public static void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = Optional.ofNullable(pageDomain.getPageNum()).orElse(1);
        Integer pageSize = Optional.ofNullable(pageDomain.getPageSize()).orElse(20);
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage() {
        PageHelper.clearPage();
    }
}
