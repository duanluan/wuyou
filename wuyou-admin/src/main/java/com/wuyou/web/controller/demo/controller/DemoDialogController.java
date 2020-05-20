package com.wuyou.web.controller.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 模态窗口
 *
 * @author wuyou
 */
@RequestMapping("/demo/modal")
@Controller
public class DemoDialogController {

  private static final String PREFIX = "demo/modal";

  /**
   * 模态窗口
   */
  @GetMapping("/dialog")
  public String dialog() {
    return PREFIX + "/dialog";
  }

  /**
   * 弹层组件
   */
  @GetMapping("/layer")
  public String layer() {
    return PREFIX + "/layer";
  }

  /**
   * 表单
   */
  @GetMapping("/form")
  public String form() {
    return PREFIX + "/form";
  }

  /**
   * 表格
   */
  @GetMapping("/table")
  public String table() {
    return PREFIX + "/table";
  }

  /**
   * 表格check
   */
  @GetMapping("/check")
  public String check() {
    return PREFIX + "/table/check";
  }

  /**
   * 表格radio
   */
  @GetMapping("/radio")
  public String radio() {
    return PREFIX + "/table/radio";
  }

  /**
   * 表格回传父窗体
   */
  @GetMapping("/parent")
  public String parent() {
    return PREFIX + "/table/parent";
  }
}
