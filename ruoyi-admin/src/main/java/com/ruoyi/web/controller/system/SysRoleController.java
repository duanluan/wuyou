package com.ruoyi.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.core.domain.Result.*;

/**
 * 角色信息
 *
 * @author ruoyi
 */
@RequestMapping("/system/role")
@Controller
public class SysRoleController extends BaseController {

  private static final String PREFIX = "system/role";

  @Autowired
  private ISysRoleService roleService;
  @Autowired
  private ISysUserService userService;
  @Autowired
  private ISysUserRoleService userRoleService;

  @RequiresPermissions("system:role:view")
  @GetMapping
  public String role() {
    return PREFIX + "/role";
  }

  @RequiresPermissions("system:role:list")
  @ResponseBody
  @PostMapping("/list")
  public Result list(Page<SysRole> page, SysRole role) {
    return Result.success(roleService.page(page, role));
  }

  @Log(title = "角色管理", businessType = BusinessType.EXPORT)
  @RequiresPermissions("system:role:export")
  @ResponseBody
  @PostMapping("/export")
  public Result export(SysRole role) {
    List<SysRole> list = roleService.list(role);
    ExcelUtil<SysRole> util = new ExcelUtil<>(SysRole.class);
    return util.exportExcel(list, "角色数据");
  }

  /**
   * 新增角色
   */
  @GetMapping("/add")
  public String add() {
    return PREFIX + "/add";
  }

  /**
   * 新增保存角色
   */
  @Log(title = "角色管理", businessType = BusinessType.INSERT)
  @RequiresPermissions("system:role:add")
  @ResponseBody
  @PostMapping("/add")
  public Result addSave(@Validated SysRole role) {
    if (roleService.checkNameUnique(role)) {
      return error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
    } else if (roleService.checkKeyUnique(role)) {
      return error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
    }
    role.setCreateBy(ShiroUtils.getLoginName());
    ShiroUtils.clearCachedAuthorizationInfo();
    return custom(roleService.insert(role));

  }

  /**
   * 修改角色
   */
  @GetMapping("/edit/{roleId}")
  public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
    mmap.put("role", roleService.getById(roleId));
    return PREFIX + "/edit";
  }

  /**
   * 修改保存角色
   */
  @Log(title = "角色管理", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:role:edit")
  @ResponseBody
  @PostMapping("/edit")
  public Result editSave(@Validated SysRole role) {
    roleService.checkAllowed(role);
    if (roleService.checkNameUnique(role)) {
      return error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
    } else if (roleService.checkKeyUnique(role)) {
      return error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
    }
    role.setUpdateBy(ShiroUtils.getLoginName());
    ShiroUtils.clearCachedAuthorizationInfo();
    return custom(roleService.update(role));
  }

  /**
   * 角色分配数据权限
   */
  @GetMapping("/authDataScope/{roleId}")
  public String authDataScope(@PathVariable("roleId") Long roleId, ModelMap mmap) {
    mmap.put("role", roleService.getById(roleId));
    return PREFIX + "/dataScope";
  }

  /**
   * 保存角色分配数据权限
   */
  @Log(title = "角色管理", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:role:edit")
  @ResponseBody
  @PostMapping("/authDataScope")
  public Result authDataScopeSave(SysRole role) {
    roleService.checkAllowed(role);
    role.setUpdateBy(ShiroUtils.getLoginName());
    if (roleService.authDataScope(role)) {
      ShiroUtils.setSysUser(userService.getById(ShiroUtils.getSysUser().getUserId()));
      return success();
    }
    return error();
  }

  @Log(title = "角色管理", businessType = BusinessType.DELETE)
  @RequiresPermissions("system:role:remove")
  @ResponseBody
  @DeleteMapping
  public Result remove(String ids) {
    try {
      return custom(roleService.deleteByIds(ids));
    } catch (Exception e) {
      return error(e.getMessage());
    }
  }

  /**
   * 校验角色名称
   *
   * @return
   */
  @ResponseBody
  @PostMapping("/checkRoleNameUnique")
  public boolean checkRoleNameUnique(SysRole role) {
    return roleService.checkNameUnique(role);
  }

  /**
   * 校验角色权限
   *
   * @return
   */
  @ResponseBody
  @PostMapping("/checkRoleKeyUnique")
  public boolean checkRoleKeyUnique(SysRole role) {
    return roleService.checkKeyUnique(role);
  }

  /**
   * 选择菜单树
   */
  @GetMapping("/selectMenuTree")
  public String selectMenuTree() {
    return PREFIX + "/tree";
  }

  /**
   * 角色状态修改
   */
  @Log(title = "角色管理", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:role:edit")
  @ResponseBody
  @PostMapping("/changeStatus")
  public Result changeStatus(SysRole role) {
    roleService.checkAllowed(role);
    return custom(roleService.changeStatus(role));
  }

  /**
   * 分配用户
   */
  @RequiresPermissions("system:role:edit")
  @GetMapping("/authUser/{roleId}")
  public String authUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
    mmap.put("role", roleService.getById(roleId));
    return PREFIX + "/authUser";
  }

  /**
   * 查询已分配用户角色列表
   */
  @RequiresPermissions("system:role:list")
  @ResponseBody
  @PostMapping("/authUser/allocatedList")
  public Result allocatedList(Page<SysUser> page, SysUser user) {
    return Result.success(userService.pageByAllocated(page, user));
  }

  /**
   * 取消授权
   */
  @Log(title = "角色管理", businessType = BusinessType.GRANT)
  @ResponseBody
  @PostMapping("/authUser/cancel")
  public Result cancelAuthUser(SysUserRole userRole) {
    return custom(userRoleService.remove(new QueryWrapper<>(userRole)));
  }

  /**
   * 批量取消授权
   */
  @Log(title = "角色管理", businessType = BusinessType.GRANT)
  @ResponseBody
  @PostMapping("/authUser/cancelAll")
  public Result cancelAuthUserAll(Long roleId, String userIds) {
    return custom(roleService.deleteAuthUsers(roleId, userIds));
  }

  /**
   * 选择用户
   */
  @GetMapping("/authUser/selectUser/{roleId}")
  public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
    mmap.put("role", roleService.getById(roleId));
    return PREFIX + "/selectUser";
  }

  /**
   * 查询未分配用户角色列表
   */
  @RequiresPermissions("system:role:list")
  @ResponseBody
  @PostMapping("/authUser/unallocatedList")
  public Result unallocatedList(Page<SysUser> page, SysUser user) {
    return Result.success(userService.page(page, user));
  }

  /**
   * 批量选择用户授权
   */
  @Log(title = "角色管理", businessType = BusinessType.GRANT)
  @ResponseBody
  @PostMapping("/authUser/selectAll")
  public Result selectAuthUserAll(Long roleId, String userIds) {
    return custom(roleService.insertAuthUsers(roleId, userIds));
  }
}