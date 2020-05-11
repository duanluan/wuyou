package com.ruoyi.web.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysDictData;
import com.ruoyi.system.service.ISysDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.core.domain.Result.custom;

/**
 * 数据字典信息
 *
 * @author ruoyi
 */
@RequestMapping("/system/dict/data")
@Controller
public class SysDictDataController extends BaseController {

  private static final String PREFIX = "system/dict/data";

  @Autowired
  private ISysDictDataService dictDataService;

  @RequiresPermissions("system:dict:view")
  @GetMapping
  public String dictData() {
    return PREFIX + "/data";
  }

  @RequiresPermissions("system:dict:list")
  @ResponseBody
  @PostMapping("/list")
  public Result list(Page<SysDictData> page, SysDictData dictData) {
    return Result.success(dictDataService.page(page, dictData));
  }

  @Log(title = "字典数据", businessType = BusinessType.EXPORT)
  @RequiresPermissions("system:dict:export")
  @ResponseBody
  @PostMapping("/export")
  public Result export(SysDictData dictData) {
    List<SysDictData> list = dictDataService.list(dictData);
    ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
    return util.exportExcel(list, "字典数据");
  }

  /**
   * 新增字典类型
   */
  @GetMapping("/add/{dictType}")
  public String add(@PathVariable("dictType") String dictType, ModelMap mmap) {
    mmap.put("dictType", dictType);
    return PREFIX + "/add";
  }

  /**
   * 新增保存字典类型
   */
  @Log(title = "字典数据", businessType = BusinessType.INSERT)
  @RequiresPermissions("system:dict:add")
  @ResponseBody
  @PostMapping("/add")
  public Result addSave(@Validated SysDictData dict) {
    dict.setCreateBy(ShiroUtils.getLoginName());
    return custom(dictDataService.save(dict));
  }

  /**
   * 修改字典类型
   */
  @GetMapping("/edit/{dictCode}")
  public String edit(@PathVariable("dictCode") Long dictCode, ModelMap mmap) {
    mmap.put("dict", dictDataService.getById(dictCode));
    return PREFIX + "/edit";
  }

  /**
   * 修改保存字典类型
   */
  @Log(title = "字典数据", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:dict:edit")
  @ResponseBody
  @PostMapping("/edit")
  public Result editSave(@Validated SysDictData dict) {
    dict.setUpdateBy(ShiroUtils.getLoginName());
    return custom(dictDataService.update(dict));
  }

  @Log(title = "字典数据", businessType = BusinessType.DELETE)
  @RequiresPermissions("system:dict:remove")
  @ResponseBody
  @DeleteMapping
  public Result remove(String ids) {
    return custom(dictDataService.removeByIds(ids));
  }
}
