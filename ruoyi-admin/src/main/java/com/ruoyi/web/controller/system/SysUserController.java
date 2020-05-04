package com.ruoyi.web.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ruoyi.common.core.domain.Result.*;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RequestMapping("/system/user")
@Controller
public class SysUserController extends BaseController {

  private static final String PREFIX = "system/user";

  @Autowired
  private ISysUserService userService;
  @Autowired
  private ISysRoleService roleService;
  @Autowired
  private ISysPostService postService;
  @Autowired
  private SysPasswordService passwordService;

  @RequiresPermissions("system:user:view")
  @GetMapping
  public String user() {
    return PREFIX + "/user";
  }

  @RequiresPermissions("system:user:list")
  @ResponseBody
  @PostMapping("/list")
  public Result list(Page<SysUser> page, SysUser user) {
    return Result.success(userService.page(page, user));
  }

  @RequiresPermissions("system:user:export")
  @Log(title = "用户管理", businessType = BusinessType.EXPORT)
  @ResponseBody
  @PostMapping("/export")
  public Result export(SysUser user) {
    List<SysUser> list = userService.list(user);
    ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
    return util.exportExcel(list, "用户数据");
  }

  @Log(title = "用户管理", businessType = BusinessType.IMPORT)
  @RequiresPermissions("system:user:import")
  @ResponseBody
  @PostMapping("/importData")
  public Result importData(MultipartFile file, boolean updateSupport) throws Exception {
    ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
    List<SysUser> userList = util.importExcel(file.getInputStream());
    String operName = ShiroUtils.getSysUser().getLoginName();
    String message = userService.importUser(userList, updateSupport, operName);
    return Result.success(message);
  }

  @RequiresPermissions("system:user:view")
  @ResponseBody
  @GetMapping("/importTemplate")
  public Result importTemplate() {
    ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
    return util.importTemplateExcel("用户数据");
  }

  /**
   * 新增用户
   */
  @GetMapping("/add")
  public String add(ModelMap mmap) {
    mmap.put("roles", roleService.listAll());
    mmap.put("posts", postService.list());
    return PREFIX + "/add";
  }

  /**
   * 新增保存用户
   */
  @Log(title = "用户管理", businessType = BusinessType.INSERT)
  @RequiresPermissions("system:user:add")
  @ResponseBody
  @PostMapping("/add")
  public Result addSave(@Validated SysUser user) {
    if (userService.checkLoginNameUnique(user.getLoginName())) {
      return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
    } else if (userService.checkPhoneUnique(user)) {
      return error("新增用户'" + user.getLoginName() + "'失败，手机号码已存在");
    } else if (userService.checkEmailUnique(user)) {
      return error("新增用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
    }
    user.setSalt(ShiroUtils.randomSalt());
    user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
    user.setCreateBy(ShiroUtils.getLoginName());
    return custom(userService.insert(user));
  }

  /**
   * 修改用户
   */
  @GetMapping("/edit/{userId}")
  public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
    mmap.put("user", userService.getById(userId));
    mmap.put("roles", roleService.listByUserId(userId));
    mmap.put("posts", postService.listByUser(userId));
    return PREFIX + "/edit";
  }

  /**
   * 修改保存用户
   */
  @Log(title = "用户管理", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:user:edit")
  @ResponseBody
  @PostMapping("/edit")
  public Result editSave(@Validated SysUser user) {
    userService.checkAllowed(user);
    if (userService.checkPhoneUnique(user)) {
      return error("修改用户'" + user.getLoginName() + "'失败，手机号码已存在");
    } else if (userService.checkEmailUnique(user)) {
      return error("修改用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
    }
    user.setUpdateBy(ShiroUtils.getLoginName());
    return custom(userService.update(user));
  }

  @Log(title = "重置密码", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:user:resetPwd")
  @GetMapping("/resetPwd/{userId}")
  public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
    mmap.put("user", userService.getById(userId));
    return PREFIX + "/resetPwd";
  }

  @Log(title = "重置密码", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:user:resetPwd")
  @ResponseBody
  @PostMapping("/resetPwd")
  public Result resetPwdSave(SysUser user) {
    userService.checkAllowed(user);
    user.setSalt(ShiroUtils.randomSalt());
    user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
    if (userService.updateById(user)) {
      if (ShiroUtils.getUserId().longValue() == user.getUserId().longValue()) {
        ShiroUtils.setSysUser(userService.getById(user.getUserId()));
      }
      return success();
    }
    return error();
  }

  /**
   * 进入授权角色页
   */
  @GetMapping("/authRole/{userId}")
  public String authRole(@PathVariable("userId") Long userId, ModelMap mmap) {
    SysUser user = userService.getById(userId);
    // 获取用户所属的角色列表
    List<SysUserRole> userRoles = userService.listUserRoleByUserId(userId);
    mmap.put("user", user);
    mmap.put("userRoles", userRoles);
    return PREFIX + "/authRole";
  }

  /**
   * 用户授权角色
   */
  @Log(title = "用户管理", businessType = BusinessType.GRANT)
  @RequiresPermissions("system:user:add")
  @ResponseBody
  @PostMapping("/authRole/insertAuthRole")
  public Result insertAuthRole(Long userId, Long[] roleIds) {
    userService.insertUserAuth(userId, roleIds);
    return success();
  }

  @Log(title = "用户管理", businessType = BusinessType.DELETE)
  @RequiresPermissions("system:user:remove")
  @ResponseBody
  @PostMapping("/remove")
  public Result remove(String ids) {
    try {
      return custom(userService.deleteByIds(ids));
    } catch (Exception e) {
      return error(e.getMessage());
    }
  }

  /**
   * 校验用户名
   */
  @ResponseBody
  @PostMapping("/checkLoginNameUnique")
  public boolean checkLoginNameUnique(SysUser user) {
    return userService.checkLoginNameUnique(user.getLoginName());
  }

  /**
   * 校验手机号码
   * @return
   */
  @ResponseBody
  @PostMapping("/checkPhoneUnique")
  public boolean checkPhoneUnique(SysUser user) {
    return userService.checkPhoneUnique(user);
  }

  /**
   * 校验email邮箱
   * @return
   */
  @ResponseBody
  @PostMapping("/checkEmailUnique")
  public boolean checkEmailUnique(SysUser user) {
    return userService.checkEmailUnique(user);
  }

  /**
   * 用户状态修改
   */
  @Log(title = "用户管理", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:user:edit")
  @ResponseBody
  @PostMapping("/changeStatus")
  public Result changeStatus(SysUser user) {
    userService.checkAllowed(user);
    return custom(userService.changeStatus(user));
  }
}