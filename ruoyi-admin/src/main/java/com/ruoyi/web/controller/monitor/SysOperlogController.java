package com.ruoyi.web.controller.monitor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.service.ISysOperLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.core.domain.Result.custom;
import static com.ruoyi.common.core.domain.Result.success;

/**
 * 操作日志记录
 *
 * @author ruoyi
 */
@RequestMapping("/monitor/operlog")
@Controller
public class SysOperlogController extends BaseController {

  private static final String PREFIX = "monitor/operlog";

  @Autowired
  private ISysOperLogService operLogService;

  @GetMapping
  @RequiresPermissions("monitor:operlog:view")
  public String operlog() {
    return PREFIX + "/operlog";
  }

  @RequiresPermissions("monitor:operlog:list")
  @ResponseBody
  @PostMapping("/list")
  public Result list(Page<SysOperLog> page, SysOperLog operLog) {
    return success(operLogService.page(page, operLog));
  }

  @Log(title = "操作日志", businessType = BusinessType.EXPORT)
  @RequiresPermissions("monitor:operlog:export")
  @ResponseBody
  @PostMapping("/export")
  public Result export(SysOperLog operLog) {
    List<SysOperLog> list = operLogService.list(operLog);
    ExcelUtil<SysOperLog> util = new ExcelUtil<>(SysOperLog.class);
    return util.exportExcel(list, "操作日志");
  }

  @RequiresPermissions("monitor:operlog:remove")
  @ResponseBody
  @PostMapping("/remove")
  public Result remove(String ids) {
    return custom(operLogService.removeByIds(ids));
  }

  @RequiresPermissions("monitor:operlog:detail")
  @GetMapping("/detail/{operId}")
  public String detail(@PathVariable("operId") Long operId, ModelMap mmap) {
    mmap.put("operLog", operLogService.getById(operId));
    return PREFIX + "/detail";
  }

  @Log(title = "操作日志", businessType = BusinessType.CLEAN)
  @RequiresPermissions("monitor:operlog:remove")
  @ResponseBody
  @PostMapping("/clean")
  public Result clean() {
    operLogService.clean();
    return success();
  }
}
