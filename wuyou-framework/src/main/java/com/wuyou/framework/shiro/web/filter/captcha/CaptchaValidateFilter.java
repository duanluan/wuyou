package com.wuyou.framework.shiro.web.filter.captcha;

import com.wuyou.common.constant.ShiroConstants;
import com.wuyou.framework.util.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证码过滤器
 *
 * @author wuyou
 */
public class CaptchaValidateFilter extends AccessControlFilter {
  /**
   * 是否开启验证码
   */
  private boolean captchaEnabled = true;
  /**
   * 验证码类型
   */
  private int captchaType = 5;

  public void setCaptchaEnabled(boolean captchaEnabled) {
    this.captchaEnabled = captchaEnabled;
  }

  public void setCaptchaType(int captchaType) {
    this.captchaType = captchaType;
  }

  /**
   * 调用前
   *
   * @param request
   * @param response
   * @param mappedValue
   * @return
   * @throws Exception
   */
  @Override
  public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    // 是否开启验证码
    request.setAttribute(ShiroConstants.CURRENT_ENABLED, captchaEnabled);
    // 验证码类型
    request.setAttribute(ShiroConstants.CURRENT_TYPE, captchaType);
    return super.onPreHandle(request, response, mappedValue);
  }

  /**
   * 调用中
   *
   * @param request
   * @param response
   * @param mappedValue
   * @return 是否允许访问
   */
  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    // 验证码禁用 或不是表单提交 允许访问
    if (!captchaEnabled || !"post".equals(httpServletRequest.getMethod().toLowerCase())) {
      return true;
    }
    return validateResponse(httpServletRequest, httpServletRequest.getParameter(ShiroConstants.CURRENT_VALIDATECODE));
  }

  public boolean validateResponse(HttpServletRequest request, String validateCode) {
    Object obj = ShiroUtils.getSession().getAttribute(CaptchaConstants.SESSION_KEY);
    String code = String.valueOf(obj != null ? obj : "");
    //无论验证码是否正确，凡验证过一次后都应将原值不可用,直到页面重新请求验证码,以防恶意用户持有该验证码进行针对后台发包的暴力破解
    request.getSession().setAttribute(CaptchaConstants.SESSION_KEY, ShiroConstants.CAPTCHA_ERROR);
    return !StringUtils.isEmpty(validateCode) && validateCode.equalsIgnoreCase(code);
  }

  /**
   * 访问拒绝时
   *
   * @param request
   * @param response
   * @return
   */
  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
    // 验证码错误
    request.setAttribute(ShiroConstants.CURRENT_CAPTCHA, ShiroConstants.CAPTCHA_ERROR);
    return true;
  }
}
