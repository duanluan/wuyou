package com.ruoyi.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysDictType;
import com.ruoyi.system.service.ISysDictTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.core.domain.Result.*;

/**
 * 数据字典信息
 *
 * @author ruoyi
 */
@RequestMapping("/system/dict")
@Controller
public class SysDictTypeController extends BaseController {

  private static final String PREFIX = "system/dict/type";

  @Autowired
  private ISysDictTypeService dictTypeService;

  @RequiresPermissions("system:dict:view")
  @GetMapping
  public String dictType() {
    return PREFIX + "/type";
  }

  @RequiresPermissions("system:dict:list")
  @ResponseBody
  @PostMapping("/list")
  public Result list(Page<SysDictType> page, SysDictType dictType) {
    return success(dictTypeService.page(page, dictType));
  }

  @Log(title = "字典类型", businessType = BusinessType.EXPORT)
  @RequiresPermissions("system:dict:export")
  @ResponseBody
  @PostMapping("/export")
  public Result export(SysDictType dictType) {

    List<SysDictType> list = dictTypeService.list(dictType);
    ExcelUtil<SysDictType> util = new ExcelUtil<>(SysDictType.class);
    return util.exportExcel(list, "字典类型");
  }

  /**
   * 新增字典类型
   */
  @GetMapping("/add")
  public String add() {
    return PREFIX + "/add";
  }

  /**
   * 新增保存字典类型
   */
  @Log(title = "字典类型", businessType = BusinessType.INSERT)
  @RequiresPermissions("system:dict:add")
  @ResponseBody
  @PostMapping("/add")
  public Result addSave(@Validated SysDictType dict) {
    if (dictTypeService.checkTypeUnique(dict)) {
      return error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
    }
    dict.setCreateBy(ShiroUtils.getLoginName());
    return custom(dictTypeService.save(dict));
  }

  /**
   * 修改字典类型
   */
  @GetMapping("/edit/{dictId}")
  public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap) {
    mmap.put("dict", dictTypeService.getById(dictId));
    return PREFIX + "/edit";
  }

  /**
   * 修改保存字典类型
   */
  @Log(title = "字典类型", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:dict:edit")
  @ResponseBody
  @PostMapping("/edit")
  public Result editSave(@Validated SysDictType dict) {
    if (dictTypeService.checkTypeUnique(dict)) {
      return error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
    }
    dict.setUpdateBy(ShiroUtils.getLoginName());
    return custom(dictTypeService.update(dict));
  }

  @Log(title = "字典类型", businessType = BusinessType.DELETE)
  @RequiresPermissions("system:dict:remove")
  @ResponseBody
  @DeleteMapping
  public Result remove(String ids) {
    return custom(dictTypeService.removeByIds(ids));
  }

  /**
   * 清空缓存
   */
  @Log(title = "字典类型", businessType = BusinessType.CLEAN)
  @RequiresPermissions("system:dict:remove")
  @ResponseBody
  @GetMapping("/clearCache")
  public Result clearCache() {
    dictTypeService.clearCache();
    return success();
  }

  /**
   * 查询字典详细
   */
  @RequiresPermissions("system:dict:list")
  @GetMapping("/detail/{dictId}")
  public String detail(@PathVariable("dictId") Long dictId, ModelMap mmap) {
    mmap.put("dict", dictTypeService.getById(dictId));
    mmap.put("dictList", dictTypeService.list());
    return "system/dict/data/data";
  }

  /**
   * 校验字典类型
   * @return
   */
  @PostMapping("/checkDictTypeUnique")
  @ResponseBody
  public boolean checkDictTypeUnique(SysDictType dictType) {
    return dictTypeService.checkTypeUnique(dictType);
  }

  /**
   * 选择字典树
   */
  @GetMapping("/selectDictTree/{columnId}/{dictType}")
  public String selectDeptTree(@PathVariable("columnId") Long columnId, @PathVariable("dictType") String dictType, ModelMap mmap) {
    mmap.put("columnId", columnId);
    SysDictType sysDictTypeQuerier = new SysDictType();
    sysDictTypeQuerier.setDictType(dictType);
    mmap.put("dict", dictTypeService.getOne(new QueryWrapper<>(sysDictTypeQuerier)));
    return PREFIX + "/tree";
  }

  /**
   * 加载字典列表树
   */
  @ResponseBody
  @GetMapping("/treeData")
  public List<Ztree> treeData() {
    List<Ztree> ztrees = dictTypeService.listTree(new SysDictType());
    return ztrees;
  }
}
