package com.wuyou.web.controller.monitor;

import com.wuyou.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * druid 监控
 *
 * @author wuyou
 */
@RequestMapping("/monitor/data")
@Controller
public class DruidController extends BaseController {

  private static final String PREFIX = "/druid";

  @RequiresPermissions("monitor:data:view")
  @GetMapping
  public String index() {
    return redirect(PREFIX + "/index");
  }
}
