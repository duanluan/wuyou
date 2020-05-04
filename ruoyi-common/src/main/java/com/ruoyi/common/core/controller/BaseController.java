package com.ruoyi.common.core.controller;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.Date;


/**
 * web层通用数据处理
 *
 * @author ruoyi
 */
@Slf4j
public class BaseController {
  /**
   * 将前台传递过来的日期格式的字符串，自动转化为Date类型
   */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    // Date 类型转换
    binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) {
        setValue(DateUtils.parseDate(text));
      }
    });
  }

  /**
   * 获取request
   */
  public HttpServletRequest getRequest() {
    return ServletUtils.getRequest();
  }

  /**
   * 获取response
   */
  public HttpServletResponse getResponse() {
    return ServletUtils.getResponse();
  }

  /**
   * 获取session
   */
  public HttpSession getSession() {
    return getRequest().getSession();
  }

  /**
   * 页面跳转
   */
  public String redirect(String url) {
    return StringUtils.format("redirect:{}", url);
  }
}
