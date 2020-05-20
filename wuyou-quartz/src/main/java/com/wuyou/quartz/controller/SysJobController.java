package com.wuyou.quartz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.common.annotation.Log;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.enums.BusinessType;
import com.wuyou.common.exception.job.TaskException;
import com.wuyou.common.utils.poi.ExcelUtil;
import com.wuyou.quartz.domain.SysJob;
import com.wuyou.quartz.service.ISysJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wuyou.common.core.domain.Result.custom;
import static com.wuyou.common.core.domain.Result.success;

/**
 * 调度任务信息操作处理
 *
 * @author wuyou
 */
@Controller
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController {

  private static final String PREFIX = "monitor/job";

  @Autowired
  private ISysJobService jobService;

  @RequiresPermissions("monitor:job:view")
  @GetMapping
  public String job() {
    return PREFIX + "/job";
  }

  @RequiresPermissions("monitor:job:list")
  @PostMapping("/list")
  @ResponseBody
  public Result list(Page<SysJob> page, SysJob job) {
    return success(jobService.page(page, job));
  }

  @Log(title = "定时任务", businessType = BusinessType.EXPORT)
  @RequiresPermissions("monitor:job:export")
  @PostMapping("/export")
  @ResponseBody
  public Result export(SysJob job) {
    List<SysJob> list = jobService.list(job);
    ExcelUtil<SysJob> util = new ExcelUtil<>(SysJob.class);
    return util.exportExcel(list, "定时任务");
  }

  @Log(title = "定时任务", businessType = BusinessType.DELETE)
  @RequiresPermissions("monitor:job:remove")
  @DeleteMapping
  @ResponseBody
  public Result remove(String ids) throws SchedulerException {
    jobService.deleteJobByIds(ids);
    return success();
  }

  @RequiresPermissions("monitor:job:detail")
  @GetMapping("/detail/{jobId}")
  public String detail(@PathVariable("jobId") Long jobId, ModelMap mmap) {
    mmap.put("name", "job");
    mmap.put("job", jobService.getById(jobId));
    return PREFIX + "/detail";
  }

  /**
   * 任务调度状态修改
   */
  @Log(title = "定时任务", businessType = BusinessType.UPDATE)
  @RequiresPermissions("monitor:job:changeStatus")
  @PostMapping("/changeStatus")
  @ResponseBody
  public Result changeStatus(SysJob job) throws SchedulerException {
    SysJob newJob = jobService.getById(job.getJobId());
    newJob.setStatus(job.getStatus());
    return custom(jobService.changeStatus(newJob));
  }

  /**
   * 任务调度立即执行一次
   */
  @Log(title = "定时任务", businessType = BusinessType.UPDATE)
  @RequiresPermissions("monitor:job:changeStatus")
  @PostMapping("/run")
  @ResponseBody
  public Result run(SysJob job) throws SchedulerException {
    jobService.run(job);
    return success();
  }

  /**
   * 新增调度
   */
  @GetMapping("/add")
  public String add() {
    return PREFIX + "/add";
  }

  /**
   * 新增保存调度
   */
  @Log(title = "定时任务", businessType = BusinessType.INSERT)
  @RequiresPermissions("monitor:job:add")
  @PostMapping("/add")
  @ResponseBody
  public Result addSave(@Validated SysJob job) throws SchedulerException, TaskException {
    return custom(jobService.insertJob(job));
  }

  /**
   * 修改调度
   */
  @GetMapping("/edit/{jobId}")
  public String edit(@PathVariable("jobId") Long jobId, ModelMap mmap) {
    mmap.put("job", jobService.getById(jobId));
    return PREFIX + "/edit";
  }

  /**
   * 修改保存调度
   */
  @Log(title = "定时任务", businessType = BusinessType.UPDATE)
  @RequiresPermissions("monitor:job:edit")
  @PostMapping("/edit")
  @ResponseBody
  public Result editSave(@Validated SysJob job) throws SchedulerException, TaskException {
    return custom(jobService.updateJob(job));
  }

  /**
   * 校验cron表达式是否有效
   */
  @PostMapping("/checkCronExpressionIsValid")
  @ResponseBody
  public boolean checkCronExpressionIsValid(SysJob job) {
    return jobService.checkCronExpressionIsValid(job.getCronExpression());
  }
}
