package com.wuyou.web.controller.monitor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.common.annotation.Log;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.enums.BusinessType;
import com.wuyou.common.utils.poi.ExcelUtil;
import com.wuyou.framework.shiro.service.SysPasswordService;
import com.wuyou.system.domain.SysLogininfor;
import com.wuyou.system.service.ISysLogininforService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wuyou.common.core.domain.Result.custom;
import static com.wuyou.common.core.domain.Result.success;

/**
 * 系统访问记录
 *
 * @author wuyou
 */
@RequestMapping("/monitor/logininfor")
@Controller
public class SysLogininforController extends BaseController {

  private static final String PREFIX = "monitor/logininfor";

  @Autowired
  private ISysLogininforService logininforService;
  @Autowired
  private SysPasswordService passwordService;

  @RequiresPermissions("monitor:logininfor:view")
  @GetMapping
  public String logininfor() {
    return PREFIX + "/logininfor";
  }

  @RequiresPermissions("monitor:logininfor:list")
  @ResponseBody
  @PostMapping("/list")
  public Result list(Page<SysLogininfor> page, SysLogininfor logininfor) {
    return success(logininforService.page(page, logininfor));
  }

  @Log(title = "登录日志", businessType = BusinessType.EXPORT)
  @RequiresPermissions("monitor:logininfor:export")
  @ResponseBody
  @PostMapping("/export")
  public Result export(SysLogininfor logininfor) {
    List<SysLogininfor> list = logininforService.list(logininfor);
    ExcelUtil<SysLogininfor> util = new ExcelUtil<>(SysLogininfor.class);
    return util.exportExcel(list, "登录日志");
  }

  @Log(title = "登录日志", businessType = BusinessType.DELETE)
  @RequiresPermissions("monitor:logininfor:remove")
  @ResponseBody
  @DeleteMapping
  public Result remove(String ids) {
    return custom(logininforService.removeByIds(ids));
  }

  @Log(title = "登录日志", businessType = BusinessType.CLEAN)
  @RequiresPermissions("monitor:logininfor:remove")
  @ResponseBody
  @PostMapping("/clean")
  public Result clean() {
    logininforService.cleanLogininfor();
    return success();
  }

  @Log(title = "账户解锁", businessType = BusinessType.OTHER)
  @RequiresPermissions("monitor:logininfor:unlock")
  @ResponseBody
  @PostMapping("/unlock")
  public Result unlock(String loginName) {
    passwordService.unlock(loginName);
    return success();
  }
}
