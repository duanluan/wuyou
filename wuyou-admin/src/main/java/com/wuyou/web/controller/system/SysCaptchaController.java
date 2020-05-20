package com.wuyou.web.controller.system;

import com.wuyou.common.core.controller.BaseController;
import com.wuyou.framework.shiro.web.filter.captcha.CaptchaConstants;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 图片验证码（支持算术形式）
 *
 * @author wuyou
 */
@RequestMapping("/captcha")
@Controller
public class SysCaptchaController extends BaseController {

  @RequestMapping
  public void getCaptcha(HttpServletRequest request, HttpServletResponse response, Integer type) throws Exception {
    Captcha captcha;
    if (type == null || type < 1 || type > 5) {
      type = 1;
    }

    if (type == 1) {
      // png
      captcha = new SpecCaptcha();
    } else if (type == 2) {
      // gif
      captcha = new GifCaptcha();
    } else if (type == 3) {
      // 中文
      captcha = new ChineseCaptcha();
    } else if (type == 4) {
      // 中文 gif
      captcha = new ChineseGifCaptcha();
    } else {
      // 算术类型
      captcha = new ArithmeticCaptcha();
    }

    // 将验证码放到 Session 中
    request.getSession().setAttribute(CaptchaConstants.SESSION_KEY, captcha.text());
    CaptchaUtil.out(captcha, request, response);
  }
}