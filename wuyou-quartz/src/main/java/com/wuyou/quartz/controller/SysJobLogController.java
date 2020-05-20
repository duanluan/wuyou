package com.wuyou.quartz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.common.annotation.Log;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.enums.BusinessType;
import com.wuyou.common.utils.poi.ExcelUtil;
import com.wuyou.quartz.domain.SysJob;
import com.wuyou.quartz.domain.SysJobLog;
import com.wuyou.quartz.service.ISysJobLogService;
import com.wuyou.quartz.service.ISysJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wuyou.common.core.domain.Result.custom;
import static com.wuyou.common.core.domain.Result.success;

/**
 * 调度日志操作处理
 *
 * @author wuyou
 */
@Controller
@RequestMapping("/monitor/jobLog")
public class SysJobLogController extends BaseController {

  private static final String PREFIX = "monitor/job";

  @Autowired
  private ISysJobService jobService;
  @Autowired
  private ISysJobLogService jobLogService;

  @RequiresPermissions("monitor:job:view")
  @GetMapping
  public String jobLog(@RequestParam(value = "jobId", required = false) Long jobId, ModelMap mmap) {
    if (jobId != null) {
      SysJob job = jobService.getById(jobId);
      mmap.put("job", job);
    }
    return PREFIX + "/jobLog";
  }

  @RequiresPermissions("monitor:job:list")
  @PostMapping("/list")
  @ResponseBody
  public Result list(Page<SysJobLog> page, SysJobLog jobLog) {
    return success(jobLogService.page(page, jobLog));
  }

  @Log(title = "调度日志", businessType = BusinessType.EXPORT)
  @RequiresPermissions("monitor:job:export")
  @PostMapping("/export")
  @ResponseBody
  public Result export(SysJobLog jobLog) {
    List<SysJobLog> list = jobLogService.list(jobLog);
    ExcelUtil<SysJobLog> util = new ExcelUtil<SysJobLog>(SysJobLog.class);
    return util.exportExcel(list, "调度日志");
  }

  @Log(title = "调度日志", businessType = BusinessType.DELETE)
  @RequiresPermissions("monitor:job:remove")
  @DeleteMapping
  @ResponseBody
  public Result remove(String ids) {
    return custom(jobLogService.removeByIds(ids));
  }

  @RequiresPermissions("monitor:job:detail")
  @GetMapping("/detail/{jobLogId}")
  public String detail(@PathVariable("jobLogId") Long jobLogId, ModelMap mmap) {
    mmap.put("name", "jobLog");
    mmap.put("jobLog", jobLogService.getById(jobLogId));
    return PREFIX + "/detail";
  }

  @Log(title = "调度日志", businessType = BusinessType.CLEAN)
  @RequiresPermissions("monitor:job:remove")
  @PostMapping("/clean")
  @ResponseBody
  public Result clean() {
    jobLogService.cleanJobLog();
    return success();
  }
}
