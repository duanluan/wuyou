package com.ruoyi.web.controller.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 报表
 *
 * @author ruoyi
 */
@RequestMapping("/demo/report")
@Controller
public class DemoReportController {

  private static final String PREFIX = "demo/report";

  /**
   * 百度ECharts
   */
  @GetMapping("/echarts")
  public String echarts() {
    return PREFIX + "/echarts";
  }

  /**
   * 图表插件
   */
  @GetMapping("/peity")
  public String peity() {
    return PREFIX + "/peity";
  }

  /**
   * 线状图插件
   */
  @GetMapping("/sparkline")
  public String sparkline() {
    return PREFIX + "/sparkline";
  }

  /**
   * 图表组合
   */
  @GetMapping("/metrics")
  public String metrics() {
    return PREFIX + "/metrics";
  }
}
