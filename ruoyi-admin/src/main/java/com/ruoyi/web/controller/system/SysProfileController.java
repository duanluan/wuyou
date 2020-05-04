package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.ruoyi.common.core.domain.Result.error;
import static com.ruoyi.common.core.domain.Result.success;

/**
 * 个人信息 业务处理
 *
 * @author ruoyi
 */
@Slf4j
@RequestMapping("/system/user/profile")
@Controller
public class SysProfileController extends BaseController {

  private static final String PREFIX = "system/user/profile";

  @Autowired
  private ISysUserService userService;
  @Autowired
  private SysPasswordService passwordService;

  /**
   * 个人信息
   */
  @GetMapping
  public String profile(ModelMap mmap) {
    SysUser user = ShiroUtils.getSysUser();
    mmap.put("user", user);
    mmap.put("roleGroup", userService.getUserRoleGroup(user.getUserId()));
    mmap.put("postGroup", userService.getUserPostGroup(user.getUserId()));
    return PREFIX + "/profile";
  }

  @ResponseBody
  @GetMapping("/checkPassword")
  public boolean checkPassword(String password) {
    SysUser user = ShiroUtils.getSysUser();
    if (passwordService.matches(user, password)) {
      return true;
    }
    return false;
  }

  @GetMapping("/resetPwd")
  public String resetPwd(ModelMap mmap) {
    SysUser user = ShiroUtils.getSysUser();
    mmap.put("user", userService.getById(user.getUserId()));
    return PREFIX + "/resetPwd";
  }

  @Log(title = "重置密码", businessType = BusinessType.UPDATE)
  @ResponseBody
  @PostMapping("/resetPwd")
  public Result resetPwd(String oldPassword, String newPassword) {
    SysUser user = ShiroUtils.getSysUser();
    if (StringUtils.isNotEmpty(newPassword) && passwordService.matches(user, oldPassword)) {
      user.setSalt(ShiroUtils.randomSalt());
      user.setPassword(passwordService.encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
      if (userService.updateById(user)) {
        ShiroUtils.setSysUser(userService.getById(user.getUserId()));
        return success();
      }
      return error();
    } else {
      return error("修改密码失败，旧密码错误");
    }
  }

  /**
   * 修改用户
   */
  @GetMapping("/edit")
  public String edit(ModelMap mmap) {
    SysUser user = ShiroUtils.getSysUser();
    mmap.put("user", userService.getById(user.getUserId()));
    return PREFIX + "/edit";
  }

  /**
   * 修改头像
   */
  @GetMapping("/avatar")
  public String avatar(ModelMap mmap) {
    SysUser user = ShiroUtils.getSysUser();
    mmap.put("user", userService.getById(user.getUserId()));
    return PREFIX + "/avatar";
  }

  /**
   * 修改用户
   */
  @Log(title = "个人信息", businessType = BusinessType.UPDATE)
  @ResponseBody
  @PostMapping("/update")
  public Result update(SysUser user) {
    SysUser currentUser = ShiroUtils.getSysUser();
    currentUser.setUserName(user.getUserName());
    currentUser.setEmail(user.getEmail());
    currentUser.setPhonenumber(user.getPhonenumber());
    currentUser.setSex(user.getSex());
    if (userService.updateById(currentUser)) {
      ShiroUtils.setSysUser(userService.getById(currentUser.getUserId()));
      return success();
    }
    return error();
  }

  /**
   * 保存头像
   */
  @Log(title = "个人信息", businessType = BusinessType.UPDATE)
  @ResponseBody
  @PostMapping("/updateAvatar")
  public Result updateAvatar(@RequestParam("avatarfile") MultipartFile file) {
    SysUser currentUser = ShiroUtils.getSysUser();
    try {
      if (!file.isEmpty()) {
        String avatar = FileUploadUtils.upload(Global.getAvatarPath(), file);
        currentUser.setAvatar(avatar);
        if (userService.updateById(currentUser)) {
          ShiroUtils.setSysUser(userService.getById(currentUser.getUserId()));
          return success();
        }
      }
      return error();
    } catch (Exception e) {
      log.error("修改头像失败！", e);
      return error(e.getMessage());
    }
  }
}
