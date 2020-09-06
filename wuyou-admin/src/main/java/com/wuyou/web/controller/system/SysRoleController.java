package com.wuyou.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.common.annotation.Log;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.enums.BusinessType;
import com.wuyou.common.utils.NumberUtils;
import com.wuyou.common.utils.poi.ExcelUtil;
import com.wuyou.framework.util.ShiroUtils;
import com.wuyou.system.domain.SysRole;
import com.wuyou.system.domain.SysUser;
import com.wuyou.system.domain.SysUserRole;
import com.wuyou.system.service.ISysRoleService;
import com.wuyou.system.service.ISysUserRoleService;
import com.wuyou.system.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wuyou.common.core.domain.Result.*;

/**
 * 角色信息
 *
 * @author wuyou
 */
@RequestMapping("/system/roles")
@Controller
public class SysRoleController extends BaseController {

  private static final String PREFIX = "system/role";

  @Autowired
  private ISysRoleService roleService;
  @Autowired
  private ISysUserService userService;
  @Autowired
  private ISysUserRoleService userRoleService;

  /**
   * 管理页面
   *
   * @return 管理页面路径
   */
  @RequiresPermissions("system:role:view")
  @GetMapping
  public String list() {
    return PREFIX + "/list";
  }

  /**
   * 列出
   *
   * @param page 分页对象
   * @param role 查询条件
   * @return 角色列表
   */
  @RequiresPermissions("system:role:list")
  @ResponseBody
  @GetMapping("/list")
  public Result list(Page<SysRole> page, SysRole role) {
    return Result.success(roleService.page(page, role));
  }

  /**
   * 导出 Excel
   *
   * @param role 查询条件
   * @return Excel 文件
   */
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
   * 编辑页面
   *
   * @param roleId 角色 ID
   * @param mmap
   * @return 编辑页面路径
   */
  @GetMapping("/{roleId}")
  public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
    if (NumberUtils.greaterThanZero(roleId)) {
      mmap.put("role", roleService.getById(roleId));
    }
    return PREFIX + "/edit";
  }

  /**
   * 保存
   *
   * @param role 角色
   * @return 是否成功
   */
  @Log(title = "角色管理", businessType = BusinessType.INSERT)
  @RequiresPermissions("system:role:add")
  @ResponseBody
  @PostMapping
  public Result addSave(@Validated SysRole role) {
    if (roleService.checkNameUnique(role)) {
      return error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
    } else if (roleService.checkKeyUnique(role)) {
      return error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
    }
    role.setCreateBy(ShiroUtils.getLoginName());
    ShiroUtils.clearCachedAuthorizationInfo();
    return custom(roleService.save(role));
  }

  /**
   * 修改
   *
   * @param role 角色
   * @return 是否成功
   */
  @Log(title = "角色管理", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:role:edit")
  @ResponseBody
  @PutMapping("/{roleId}")
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
   * 分配数据权限页面
   *
   * @param roleId 角色 ID
   * @param mmap
   * @return 分配数据权限页面路径
   */
  @GetMapping("/{roleId}/authDataScope")
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
  @PutMapping("/{roleId}/authDataScope")
  public Result authDataScopeSave(SysRole role) {
    roleService.checkAllowed(role);
    role.setUpdateBy(ShiroUtils.getLoginName());
    if (roleService.authDataScope(role)) {
      ShiroUtils.setSysUser(userService.getById(ShiroUtils.getSysUser().getUserId()));
      return success();
    }
    return error();
  }

  /**
   * 删除
   *
   * @param ids 角色 ID 集合
   * @return 是否成功
   */
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
   * 校验是否唯一
   *
   * @param roleId   角色 ID
   * @param roleName 角色名
   * @param roleKey  角色权限
   * @return 对应的属性是否唯一
   */
  @ResponseBody
  @PostMapping("/{roleId}/checkUnique")
  public boolean checkRoleNameUnique(@PathVariable("roleId") Long roleId, @RequestParam(required = false) String roleName, @RequestParam(required = false) String roleKey) {
    if (StringUtils.isNotBlank(roleName)) {
      SysRole sysRole = new SysRole();
      sysRole.setRoleId(roleId);
      sysRole.setRoleName(roleName);
      return roleService.checkNameUnique(sysRole);
    }
    if (StringUtils.isNotBlank(roleKey)) {
      SysRole sysRole = new SysRole();
      sysRole.setRoleId(roleId);
      sysRole.setRoleKey(roleKey);
      return roleService.checkKeyUnique(sysRole);
    }

    return false;
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
  @PutMapping("/changeStatus")
  public Result changeStatus(SysRole role) {
    roleService.checkAllowed(role);
    return custom(roleService.changeStatus(role));
  }

  /**
   * 分配用户
   */
  @RequiresPermissions("system:role:edit")
  @GetMapping("/{roleId}/authUser")
  public String authUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
    mmap.put("role", roleService.getById(roleId));
    return PREFIX + "/authUser";
  }

  /**
   * 查询已分配用户角色列表
   */
  @RequiresPermissions("system:role:list")
  @ResponseBody
  @GetMapping("/{roleId}/authUser/allocatedList")
  public Result allocatedList(Page<SysUser> page, SysUser user) {
    return Result.success(userService.pageByAllocated(page, user));
  }

  /**
   * 取消授权
   */
  @Log(title = "角色管理", businessType = BusinessType.GRANT)
  @ResponseBody
  @PutMapping("/{roleId}/authUser/cancel")
  public Result cancelAuthUser(SysUserRole userRole) {
    return custom(userRoleService.remove(new QueryWrapper<>(userRole)));
  }

  /**
   * 批量取消授权
   */
  @Log(title = "角色管理", businessType = BusinessType.GRANT)
  @ResponseBody
  @PutMapping("/{roleId}/authUser/cancelAll")
  public Result cancelAuthUserAll(@PathVariable("roleId") Long roleId, String userIds) {
    return custom(roleService.deleteAuthUsers(roleId, userIds));
  }

  /**
   * 选择用户
   */
  @GetMapping("/{roleId}/authUser/selectUser")
  public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
    mmap.put("role", roleService.getById(roleId));
    return PREFIX + "/selectUser";
  }

  /**
   * 查询未分配用户角色列表
   */
  @RequiresPermissions("system:role:list")
  @ResponseBody
  @GetMapping("/{roleId}/authUser/unallocatedList")
  public Result unallocatedList(Page<SysUser> page, SysUser user) {
    return Result.success(userService.page(page, user));
  }

  /**
   * 批量选择用户授权
   */
  @Log(title = "角色管理", businessType = BusinessType.GRANT)
  @ResponseBody
  @PutMapping("/{roleId}/authUser/batchSelect")
  public Result selectAuthUserAll(@PathVariable("roleId") Long roleId, String userIds) {
    return custom(roleService.insertAuthUsers(roleId, userIds));
  }
}