package com.wuyou.web.controller.tool;

import com.wuyou.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * build 表单构建
 *
 * @author wuyou
 */
@RequestMapping("/tool/build")
@Controller
public class BuildController extends BaseController {

  private static final String PREFIX = "tool/build";

  @RequiresPermissions("tool:build:view")
  @GetMapping
  public String build() {
    return PREFIX + "/build";
  }
}
