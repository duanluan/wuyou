package com.ruoyi.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysMenu;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.service.ISysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.core.domain.Result.custom;
import static com.ruoyi.common.core.domain.Result.error;

/**
 * 菜单信息
 *
 * @author ruoyi
 */
@RequestMapping("/system/menu")
@Controller
public class SysMenuController extends BaseController {

  private static final String PREFIX = "system/menu";

  @Autowired
  private ISysMenuService menuService;

  @RequiresPermissions("system:menu:view")
  @GetMapping
  public String menu() {
    return PREFIX + "/menu";
  }

  @RequiresPermissions("system:menu:list")
  @ResponseBody
  @PostMapping("/list")
  public List<SysMenu> list(SysMenu menu) {
    Long userId = ShiroUtils.getUserId();
    return menuService.list(menu, userId);
  }

  /**
   * 删除菜单
   */
  @Log(title = "菜单管理", businessType = BusinessType.DELETE)
  @RequiresPermissions("system:menu:remove")
  @ResponseBody
  @GetMapping("/remove/{menuId}")
  public Result remove(@PathVariable("menuId") Long menuId) {
    SysMenu sysMenuQuerier = new SysMenu();
    sysMenuQuerier.setParentId(menuId);
    if (menuService.count(new QueryWrapper<>(sysMenuQuerier)) > 0) {
      return Result.error("存在子菜单,不允许删除");
    }

    sysMenuQuerier = new SysMenu();
    sysMenuQuerier.setMenuId(menuId);
    if (menuService.count(new QueryWrapper<>(sysMenuQuerier)) > 0) {
      return Result.error("菜单已分配,不允许删除");
    }

    ShiroUtils.clearCachedAuthorizationInfo();
    sysMenuQuerier.setParentId(menuId);
    return custom(menuService.remove(new QueryWrapper<>(sysMenuQuerier)));
  }

  /**
   * 新增
   */
  @GetMapping("/add/{parentId}")
  public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
    SysMenu menu = null;
    if (0L != parentId) {
      menu = menuService.getById(parentId);
    } else {
      menu = new SysMenu();
      menu.setMenuId(0L);
      menu.setMenuName("主目录");
    }
    mmap.put("menu", menu);
    return PREFIX + "/add";
  }

  /**
   * 新增保存菜单
   */
  @Log(title = "菜单管理", businessType = BusinessType.INSERT)
  @RequiresPermissions("system:menu:add")
  @ResponseBody
  @PostMapping("/add")
  public Result addSave(@Validated SysMenu menu) {
    if (menuService.checkNameUnique(menu)) {
      return error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
    }
    menu.setCreateBy(ShiroUtils.getLoginName());
    ShiroUtils.clearCachedAuthorizationInfo();
    return custom(menuService.save(menu));
  }

  /**
   * 修改菜单
   */
  @GetMapping("/edit/{menuId}")
  public String edit(@PathVariable("menuId") Long menuId, ModelMap mmap) {
    mmap.put("menu", menuService.getById(menuId));
    return PREFIX + "/edit";
  }

  /**
   * 修改保存菜单
   */
  @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:menu:edit")
  @ResponseBody
  @PostMapping("/edit")
  public Result editSave(@Validated SysMenu menu) {
    if (menuService.checkNameUnique(menu)) {
      return error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
    }
    menu.setUpdateBy(ShiroUtils.getLoginName());
    ShiroUtils.clearCachedAuthorizationInfo();
    return custom(menuService.updateById(menu));
  }

  /**
   * 选择菜单图标
   */
  @GetMapping("/icon")
  public String icon() {
    return PREFIX + "/icon";
  }

  /**
   * 校验菜单名称
   * @return
   */
  @ResponseBody
  @PostMapping("/checkMenuNameUnique")
  public boolean checkMenuNameUnique(SysMenu menu) {
    return menuService.checkNameUnique(menu);
  }

  /**
   * 加载角色菜单列表树
   */
  @ResponseBody
  @GetMapping("/roleMenuTreeData")
  public List<Ztree> roleMenuTreeData(SysRole role) {
    Long userId = ShiroUtils.getUserId();
    return menuService.listRoleMenuTree(role, userId);
  }

  /**
   * 加载所有菜单列表树
   */
  @ResponseBody
  @GetMapping("/menuTreeData")
  public List<Ztree> menuTreeData() {
    Long userId = ShiroUtils.getUserId();
    return menuService.listMenuTree(userId);
  }

  /**
   * 选择菜单树
   */
  @GetMapping("/selectMenuTree/{menuId}")
  public String selectMenuTree(@PathVariable("menuId") Long menuId, ModelMap mmap) {
    mmap.put("menu", menuService.getById(menuId));
    return PREFIX + "/tree";
  }
}