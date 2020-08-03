package com.wuyou.web.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.common.annotation.Log;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.enums.BusinessType;
import com.wuyou.common.utils.ObjectUtils;
import com.wuyou.common.utils.poi.ExcelUtil;
import com.wuyou.framework.shiro.service.SysPasswordService;
import com.wuyou.framework.util.ShiroUtils;
import com.wuyou.system.domain.SysRole;
import com.wuyou.system.domain.SysUser;
import com.wuyou.system.domain.SysUserRole;
import com.wuyou.system.service.ISysPostService;
import com.wuyou.system.service.ISysRoleService;
import com.wuyou.system.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.wuyou.common.core.domain.Result.*;

/**
 * 用户管理
 *
 * @author wuyou
 */
@RequestMapping("system/users")
@Controller
public class SysUserController extends BaseController {

  private static final String PREFIX = "system/user";
  private static final String LOG_TITLE = "用户管理";

  @Autowired
  private ISysUserService userService;
  @Autowired
  private ISysRoleService roleService;
  @Autowired
  private ISysPostService postService;
  @Autowired
  private SysPasswordService passwordService;

  /**
   * 管理页面
   *
   * @return 管理页面路径
   */
  @RequiresPermissions("system:user:view")
  @GetMapping
  public String user() {
    return PREFIX + "/list";
  }

  /**
   * 列表
   *
   * @param page 分页对象
   * @param user 查询条件
   * @return 用户
   */
  @RequiresPermissions("system:user:list")
  @ResponseBody
  @GetMapping("/list")
  public Result list(Page<SysUser> page, SysUser user) {
    return Result.success(userService.page(page, user));
  }

  /**
   * 导出 Excel
   *
   * @param user 查询条件
   * @return 用户
   */
  @RequiresPermissions("system:user:export")
  @Log(title = LOG_TITLE, businessType = BusinessType.EXPORT)
  @ResponseBody
  @PostMapping("/export")
  public Result export(SysUser user) {
    List<SysUser> list = userService.list(user);
    ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
    return util.exportExcel(list, "用户数据");
  }

  /**
   * 导入
   *
   * @param file          文件
   * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
   * @return 消息
   * @throws Exception
   */
  @Log(title = LOG_TITLE, businessType = BusinessType.IMPORT)
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

  /**
   * 导入模板
   *
   * @return 消息
   */
  @RequiresPermissions("system:user:view")
  @ResponseBody
  @GetMapping("/importTemplate")
  public Result importTemplate() {
    ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
    return util.importTemplateExcel("用户数据");
  }

  /**
   * 编辑页面
   *
   * @param userId 用户 ID
   * @return 编辑页面路径
   */
  @GetMapping("/{userId}")
  public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
    List<SysRole> roles = roleService.listByUserId(userId);
    mmap.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
    mmap.put("posts", postService.listByUser(userId));
    if (ObjectUtils.greaterThanZero(userId)) {
      mmap.put("user", userService.getById(userId));
    }
    return PREFIX + "/edit";
  }

  /**
   * 保存
   *
   * @param user 用户
   * @return 是否成功
   */
  @Log(title = LOG_TITLE, businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:user:edit")
  @ResponseBody
  @PostMapping
  public Result save(@Validated SysUser user) {
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
   * 修改
   *
   * @param user 用户
   * @return 是否成功
   */
  @Log(title = LOG_TITLE, businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:user:edit")
  @ResponseBody
  @PutMapping("/{userId}")
  public Result update(@PathVariable("userId") Long userId, @Validated SysUser user) {
    userService.checkAllowed(user);
    if (userService.checkPhoneUnique(user)) {
      return error("修改用户'" + user.getLoginName() + "'失败，手机号码已存在");
    } else if (userService.checkEmailUnique(user)) {
      return error("修改用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
    }
    user.setUpdateBy(ShiroUtils.getLoginName());
    return custom(userService.update(user));
  }

  /**
   * 删除
   *
   * @param ids 用户 ID 集合
   * @return 是否成功
   */
  @Log(title = LOG_TITLE, businessType = BusinessType.DELETE)
  @RequiresPermissions("system:user:remove")
  @ResponseBody
  @DeleteMapping
  public Result remove(String ids) {
    try {
      return custom(userService.deleteByIds(ids));
    } catch (Exception e) {
      return error(e.getMessage());
    }
  }

  /**
   * 重置密码页面
   *
   * @param userId 用户 ID
   * @return 重置密码页面路径
   */
  @Log(title = "重置密码", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:user:resetPwd")
  @GetMapping("/{userId}/resetPwd")
  public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
    mmap.put("user", userService.getById(userId));
    return PREFIX + "/resetPwd";
  }

  /**
   * 重置密码
   *
   * @param user 用户
   * @return 是否成功
   */
  @Log(title = "重置密码", businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:user:resetPwd")
  @ResponseBody
  @PutMapping("/{userId}/resetPwd")
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
   * 授权角色页面
   *
   * @param userId 用户 ID
   * @return 授权角色页面路径
   */
  @GetMapping("/{userId}/authRole")
  public String authRole(@PathVariable("userId") Long userId, ModelMap mmap) {
    SysUser user = userService.getById(userId);
    // 获取用户所属的角色列表
    List<SysUserRole> userRoles = userService.listUserRoleByUserId(userId);
    mmap.put("user", user);
    mmap.put("userRoles", userRoles);
    return PREFIX + "/authRole";
  }

  /**
   * 授权角色
   *
   * @param userId  用户 ID
   * @param roleIds 角色 ID 数组
   * @return 是否成功
   */
  @Log(title = LOG_TITLE, businessType = BusinessType.GRANT)
  @RequiresPermissions("system:user:add")
  @ResponseBody
  @PutMapping("/{userId}/authRole")
  public Result insertAuthRole(@PathVariable("userId") Long userId, Long[] roleIds) {
    userService.insertUserAuth(userId, roleIds);
    return success();
  }

  /**
   * 检查是否唯一
   *
   * @param userId      用户 ID
   * @param loginName   用户名
   * @param phonenumber 手机号码
   * @param email       邮箱
   * @return 对应的属性是否唯一
   */
  @ResponseBody
  @GetMapping("/{userId}/checkUnique")
  public boolean checkUnique(@PathVariable("userId") Long userId, @RequestParam(required = false) String loginName, @RequestParam(required = false) String phonenumber, @RequestParam(required = false) String email) {
    if (StringUtils.isNotBlank(loginName)) {
      return userService.checkLoginNameUnique(loginName);
    }
    if (StringUtils.isNotBlank(phonenumber)) {
      SysUser sysUser = new SysUser();
      sysUser.setUserId(userId);
      sysUser.setPhonenumber(phonenumber);
      return userService.checkPhoneUnique(sysUser);
    }
    if (StringUtils.isNotBlank(email)) {
      SysUser sysUser = new SysUser();
      sysUser.setUserId(userId);
      sysUser.setEmail(email);
      return userService.checkEmailUnique(sysUser);
    }

    return false;
  }

  /**
   * 修改用户状态
   *
   * @param user 用户
   * @return 是否成功
   */
  @Log(title = LOG_TITLE, businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:user:edit")
  @ResponseBody
  @PutMapping("/changeStatus")
  public Result changeStatus(SysUser user) {
    userService.checkAllowed(user);
    return custom(userService.changeStatus(user));
  }
}