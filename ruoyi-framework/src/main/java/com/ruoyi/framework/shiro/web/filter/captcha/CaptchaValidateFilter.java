package com.ruoyi.framework.shiro.web.filter.captcha;

import com.ruoyi.common.constant.ShiroConstants;
import com.ruoyi.framework.util.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证码过滤器
 *
 * @author ruoyi
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
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    // 验证码禁用或不是表单提交时允许访问
    if (!captchaEnabled || !"post".equals(httpServletRequest.getMethod().toLowerCase())) {
      return true;
    }

    // 获取验证码
    String validateCode = httpServletRequest.getParameter(ShiroConstants.CURRENT_VALIDATECODE);
    // 验证码是否正确
    Object obj = ShiroUtils.getSession().getAttribute(CaptchaConstants.SESSION_KEY);
    String code = String.valueOf(obj != null ? obj : "");
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
