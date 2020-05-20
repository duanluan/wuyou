package com.wuyou.common.core.page;

import com.wuyou.common.constant.Constants;
import com.wuyou.common.utils.ServletUtils;

/**
 * 表格数据处理
 *
 * @author wuyou
 */
public class TableSupport {
  /**
   * 封装分页对象
   */
  public static PageDomain getPageDomain() {
    PageDomain pageDomain = new PageDomain();
    pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
    pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
    pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
    pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
    return pageDomain;
  }

  public static PageDomain buildPageRequest() {
    return getPageDomain();
  }
}
