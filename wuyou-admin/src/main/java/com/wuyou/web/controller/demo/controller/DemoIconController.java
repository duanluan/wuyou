package com.wuyou.web.controller.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 图标相关
 *
 * @author wuyou
 */
@RequestMapping("/demo/icon")
@Controller
public class DemoIconController {

  private static final String PREFIX = "demo/icon";

  /**
   * FontAwesome图标
   */
  @GetMapping("/fontawesome")
  public String fontAwesome() {
    return PREFIX + "/fontawesome";
  }

  /**
   * Glyphicons图标
   */
  @GetMapping("/glyphicons")
  public String glyphicons() {
    return PREFIX + "/glyphicons";
  }
}
