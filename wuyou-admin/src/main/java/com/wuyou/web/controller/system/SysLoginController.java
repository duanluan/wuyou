package com.wuyou.web.controller.system;

import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.utils.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.wuyou.common.core.domain.Result.error;
import static com.wuyou.common.core.domain.Result.success;

/**
 * 登录验证
 *
 * @author wuyou
 */
@Controller
public class SysLoginController extends BaseController {

  @GetMapping("/login")
  public String login(HttpServletRequest request, HttpServletResponse response) {
    // 如果是Ajax请求，返回Json字符串。
    if (ServletUtils.isAjaxRequest(request)) {
      return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
    }

    return "login";
  }

  @ResponseBody
  @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.PUT})
  public Result ajaxLogin(String username, String password, Boolean rememberMe) {
    // TODO 弹窗后登录过期，提交会到登录接口
    UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
    Subject subject = SecurityUtils.getSubject();
    try {
      subject.login(token);
      return success();
    } catch (AuthenticationException e) {
      String msg = "用户或密码错误";
      if (StringUtils.isNotEmpty(e.getMessage())) {
        msg = e.getMessage();
      }
      return error(msg);
    }
  }

  @GetMapping("/unauth")
  public String unauth() {
    return "error/unauth";
  }
}
