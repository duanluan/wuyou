package com.wuyou.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.common.annotation.Log;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.enums.BusinessType;
import com.wuyou.common.utils.poi.ExcelUtil;
import com.wuyou.framework.util.ShiroUtils;
import com.wuyou.system.domain.SysConfig;
import com.wuyou.system.service.ISysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wuyou.common.core.domain.Result.*;

/**
 * 参数配置 信息操作处理
 *
 * @author wuyou
 */
@RequestMapping("/system/config")
@Controller
public class SysConfigController extends BaseController {

  private static final String PREFIX = "system/config";

  @Autowired
  private ISysConfigService configService;

  @RequiresPermissions("system:config:view")
  @GetMapping
  public String config() {
    return PREFIX + "/config";
  }

  /**
   * 查询参数配置列表
   */
  @RequiresPermissions("system:config:list")
  @ResponseBody
  @PostMapping("/list")
  public Result list(Page<SysConfig> page, SysConfig config) {
    return success(configService.page(page, config));
  }

  @Log(title = "参数管理", businessType = BusinessType.EXPORT)
  @RequiresPermissions("system:config:export")
  @ResponseBody
  @PostMapping("/export")
  public Result export(SysConfig config) {
    List<SysConfig> list = configService.list(new QueryWrapper<>(config));
    ExcelUtil<SysConfig> util = new ExcelUtil<>(SysConfig.class);
    return util.exportExcel(list, "参数数据");
  }

  /**
   * 新增参数配置
   */
  @GetMapping("/add")
  public String add() {
    return PREFIX + "/add";
  }

  /**
   * 新增保存参数配置
   */
  @Log(title = "参数管理", businessType = BusinessType.INSERT)
  @RequiresPermissions("system:config:add")
  @ResponseBody
  @PostMapping("/add")
  public Result addSave(@Validated SysConfig config) {
    if (configService.checkKeyUnique(config)) {
      return error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
    }
    config.setCreateBy(ShiroUtils.getLoginName());
    return custom(configService.save(config));
  }

  /**
   * 修改参数配置
   */
  @GetMapping("/edit/{configId}")
  public String edit(@PathVariable("configId") Long configId, ModelMap mmap) {
    mmap.put("config", configService.getById(configId));
    return PREFIX + "/edit";
  }

  /**
   * 修改保存参数配置
   */
  @Log(title = "参数管理", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:config:edit")
  @ResponseBody
  @PostMapping("/edit")
  public Result editSave(@Validated SysConfig config) {
    if (configService.checkKeyUnique(config)) {
      return error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
    }
    config.setUpdateBy(ShiroUtils.getLoginName());
    return custom(configService.update(config));
  }

  /**
   * 删除参数配置
   */
  @Log(title = "参数管理", businessType = BusinessType.DELETE)
  @RequiresPermissions("system:config:remove")
  @ResponseBody
  @DeleteMapping
  public Result remove(String ids) {
    return custom(configService.removeByIds(ids));
  }

  /**
   * 清空缓存
   */
  @Log(title = "参数管理", businessType = BusinessType.CLEAN)
  @RequiresPermissions("system:config:remove")
  @ResponseBody
  @GetMapping("/clearCache")
  public Result clearCache() {
    configService.clearCache();
    return success();
  }

  /**
   * 校验参数键名
   * @return
   */
  @ResponseBody
  @PostMapping("/checkConfigKeyUnique")
  public boolean checkConfigKeyUnique(SysConfig config) {
    return configService.checkKeyUnique(config);
  }
}
