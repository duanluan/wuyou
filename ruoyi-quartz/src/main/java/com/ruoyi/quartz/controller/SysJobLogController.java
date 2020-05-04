package com.ruoyi.quartz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.domain.SysJobLog;
import com.ruoyi.quartz.service.ISysJobLogService;
import com.ruoyi.quartz.service.ISysJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.core.domain.Result.custom;
import static com.ruoyi.common.core.domain.Result.success;

/**
 * 调度日志操作处理
 *
 * @author ruoyi
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
  @PostMapping("/remove")
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
