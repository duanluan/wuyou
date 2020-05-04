package com.ruoyi.framework.web.exception;

import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.exception.DemoModeException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.security.PermissionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author ruoyi
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  /**
   * 权限校验失败 如果请求为ajax返回json，普通请求跳转页面
   */
  @ExceptionHandler(AuthorizationException.class)
  public Object handleAuthorizationException(HttpServletRequest request, AuthorizationException e) {
    log.error(e.getMessage(), e);
    if (ServletUtils.isAjaxRequest(request)) {
      return Result.error(PermissionUtils.getMsg(e.getMessage()));
    } else {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("error/unauth");
      return modelAndView;
    }
  }

  /**
   * 请求方式不支持
   */
  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  public Result handleException(HttpRequestMethodNotSupportedException e) {
    log.error(e.getMessage(), e);
    return Result.error("不支持' " + e.getMethod() + "'请求");
  }

  /**
   * 拦截未知的运行时异常
   */
  @ExceptionHandler(RuntimeException.class)
  public Result notFount(RuntimeException e) {
    log.error("运行时异常:", e);
    return Result.error("运行时异常:" + e.getMessage());
  }

  /**
   * 系统异常
   */
  @ExceptionHandler(Exception.class)
  public Result handleException(Exception e) {
    log.error(e.getMessage(), e);
    return Result.error("服务器错误，请联系管理员");
  }

  /**
   * 业务异常
   */
  @ExceptionHandler(BusinessException.class)
  public Object businessException(HttpServletRequest request, BusinessException e) {
    log.error(e.getMessage(), e);
    if (ServletUtils.isAjaxRequest(request)) {
      return Result.error(e.getMessage());
    } else {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("errorMessage", e.getMessage());
      modelAndView.setViewName("error/business");
      return modelAndView;
    }
  }

  /**
   * 自定义验证异常
   */
  @ExceptionHandler(BindException.class)
  public Result validatedBindException(BindException e) {
    log.error(e.getMessage(), e);
    String message = e.getAllErrors().get(0).getDefaultMessage();
    return Result.error(message);
  }

  /**
   * 演示模式异常
   */
  @ExceptionHandler(DemoModeException.class)
  public Result demoModeException(DemoModeException e) {
    return Result.error("演示模式，不允许操作");
  }
}
