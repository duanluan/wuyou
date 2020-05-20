package com.wuyou.web.controller.system;

import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.framework.shiro.service.SysRegisterService;
import com.wuyou.system.domain.SysUser;
import com.wuyou.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.wuyou.common.core.domain.Result.error;
import static com.wuyou.common.core.domain.Result.success;

/**
 * 注册验证
 *
 * @author wuyou
 */
@Controller
public class SysRegisterController extends BaseController {

  @Autowired
  private SysRegisterService registerService;
  @Autowired
  private ISysConfigService configService;

  @GetMapping("/register")
  public String register() {
    return "register";
  }

  @ResponseBody
  @PostMapping("/register")
  public Result ajaxRegister(SysUser user) {
    if (!("true".equals(configService.getValueByKey("sys.account.registerUser")))) {
      return error("当前系统没有开启注册功能！");
    }
    String msg = registerService.register(user);
    return StringUtils.isEmpty(msg) ? success() : error(msg);
  }
}
