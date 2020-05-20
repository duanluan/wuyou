package com.wuyou.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.common.annotation.DataScope;
import com.wuyou.common.constant.UserConstants;
import com.wuyou.common.exception.BusinessException;
import com.wuyou.common.utils.security.Md5Utils;
import com.wuyou.system.domain.*;
import com.wuyou.system.mapper.*;
import com.wuyou.system.service.ISysConfigService;
import com.wuyou.system.service.ISysUserPostService;
import com.wuyou.system.service.ISysUserRoleService;
import com.wuyou.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author wuyou
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

  @Autowired
  private SysUserMapper userMapper;
  @Autowired
  private SysRoleMapper roleMapper;
  @Autowired
  private SysPostMapper postMapper;
  @Autowired
  private SysUserPostMapper userPostMapper;
  @Autowired
  private SysUserRoleMapper userRoleMapper;
  @Autowired
  private ISysConfigService configService;

  @Autowired
  private ISysUserPostService userPostService;
  @Autowired
  private ISysUserRoleService userRoleService;

  @Override
  public IPage<SysUser> page(Page<SysUser> page, SysUser user) {
    return page.setRecords(userMapper.page(page, user));
  }

  /**
   * 根据条件分页查询用户列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @DataScope(deptAlias = "d", userAlias = "u")
  @Override
  public List<SysUser> list(SysUser user) {
    return userMapper.page(null, user);
  }

  @DataScope(deptAlias = "d", userAlias = "u")
  @Override
  public IPage<SysUser> pageByAllocated(Page<SysUser> page, SysUser user) {
    return page.setRecords(userMapper.pageByAllocated(page, user));
  }

  /**
   * 根据条件分页查询已分配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @DataScope(deptAlias = "d", userAlias = "u")
  @Override
  public List<SysUser> listAllocated(SysUser user) {
    return userMapper.listAllocated(user);
  }

  /**
   * 根据条件分页查询未分配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @DataScope(deptAlias = "d", userAlias = "u")
  @Override
  public List<SysUser> listUnallocated(SysUser user) {
    return userMapper.listUnallocated(user);
  }

  /**
   * 通过用户名查询用户
   *
   * @param userName 用户名
   * @return 用户对象信息
   */
  @Override
  public SysUser getByLoginName(String userName) {
    return userMapper.getByLoginName(userName);
  }

  /**
   * 通过手机号码查询用户
   *
   * @param phoneNumber 手机号码
   * @return 用户对象信息
   */
  @Override
  public SysUser getByPhoneNumber(String phoneNumber) {
    return userMapper.getByPhoneNumber(phoneNumber);
  }

  /**
   * 通过邮箱查询用户
   *
   * @param email 邮箱
   * @return 用户对象信息
   */
  @Override
  public SysUser getByEmail(String email) {
    return userMapper.getByEmail(email);
  }

  /**
   * 通过用户ID查询用户
   *
   * @param userId 用户ID
   * @return 用户对象信息
   */
  @Override
  public SysUser getById(Long userId) {
    return userMapper.getById(userId);
  }

  /**
   * 通过用户ID查询用户和角色关联
   *
   * @param userId 用户ID
   * @return 用户和角色关联列表
   */
  @Override
  public List<SysUserRole> listUserRoleByUserId(Long userId) {
    SysUserRole sysUserRoleQuerier = new SysUserRole();
    sysUserRoleQuerier.setUserId(userId);
    return userRoleMapper.selectList(new QueryWrapper<>(sysUserRoleQuerier));
  }

  /**
   * 通过用户ID删除用户
   *
   * @param userId 用户ID
   * @return 结果
   */
  @Override
  public int deleteById(Long userId) {
    // 删除用户与角色关联
    SysUserRole sysUserRoleQuerier = new SysUserRole();
    sysUserRoleQuerier.setUserId(userId);
    userRoleMapper.delete(new QueryWrapper<>(sysUserRoleQuerier));
    // 删除用户与岗位表
    SysUserPost sysUserPostQuerier = new SysUserPost();
    sysUserPostQuerier.setUserId(userId);
    userPostMapper.delete(new QueryWrapper<>(sysUserPostQuerier));
    return userMapper.deleteById(userId);
  }

  /**
   * 批量删除用户信息
   *
   * @param ids 需要删除的数据ID
   * @return 结果
   */
  @Override
  public boolean deleteByIds(String ids) throws BusinessException {
    String[] userIds = StringUtils.split(ids, ",");
    for (String userId : userIds) {
      checkAllowed(new SysUser(Long.valueOf(userId)));
    }
    return super.removeByIds(Arrays.asList(userIds));
  }

  /**
   * 新增保存用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Transactional
  @Override
  public boolean insert(SysUser user) {
    // 新增用户信息
    boolean result = userMapper.insert(user) > 0;
    // 新增用户岗位关联
    insertUserPost(user);
    // 新增用户与角色管理
    insertUserRole(user.getUserId(), user.getRoleIds());
    return result;
  }

  /**
   * 注册用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public boolean register(SysUser user) {
    user.setUserType(UserConstants.REGISTER_USER_TYPE);
    return super.save(user);
  }

  /**
   * 修改保存用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Transactional
  @Override
  public boolean update(SysUser user) {
    Long userId = user.getUserId();
    // 删除用户与角色关联
    SysUserRole sysUserRoleQuerier = new SysUserRole();
    sysUserRoleQuerier.setUserId(userId);
    userRoleMapper.delete(new QueryWrapper<>(sysUserRoleQuerier));
    // 新增用户与角色管理
    insertUserRole(user.getUserId(), user.getRoleIds());
    // 删除用户与岗位关联
    SysUserPost sysUserPostQuerier = new SysUserPost();
    sysUserPostQuerier.setUserId(userId);
    userPostMapper.delete(new QueryWrapper<>(sysUserPostQuerier));
    // 新增用户与岗位管理
    insertUserPost(user);
    return super.updateById(user);
  }

  /**
   * 用户授权角色
   *
   * @param userId  用户ID
   * @param roleIds 角色组
   */
  @Override
  public void insertUserAuth(Long userId, Long[] roleIds) {
    SysUserRole sysUserRoleQuerier = new SysUserRole();
    sysUserRoleQuerier.setUserId(userId);
    userRoleMapper.delete(new QueryWrapper<>(sysUserRoleQuerier));
    insertUserRole(userId, roleIds);
  }

  /**
   * 新增用户角色信息
   *
   * @param userId  用户 ID
   * @param roleIds 角色 ID 数组
   */
  public void insertUserRole(Long userId, Long[] roleIds) {
    if (roleIds != null) {
      // 新增用户与角色管理
      List<SysUserRole> list = new ArrayList<>();
      for (Long roleId : roleIds) {
        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        list.add(ur);
      }
      if (list.size() > 0) {
        userRoleService.saveBatch(list);
      }
    }
  }

  /**
   * 新增用户岗位信息
   *
   * @param user 用户对象
   */
  public void insertUserPost(SysUser user) {
    Long[] posts = user.getPostIds();
    if (posts != null) {
      // 新增用户与岗位管理
      List<SysUserPost> list = new ArrayList<>();
      for (Long postId : posts) {
        SysUserPost up = new SysUserPost();
        up.setUserId(user.getUserId());
        up.setPostId(postId);
        list.add(up);
      }
      if (list.size() > 0) {
        userPostService.saveBatch(list);
      }
    }
  }

  /**
   * 校验登录名称是否唯一
   *
   * @param loginName 用户名
   * @return
   */
  @Override
  public boolean checkLoginNameUnique(String loginName) {
    if(StringUtils.isBlank(loginName)){
      return false;
    }

    SysUser sysUserQuerier = new SysUser();
    sysUserQuerier.setLoginName(loginName);
    return super.count(new QueryWrapper<>(sysUserQuerier)) > 0;
  }

  /**
   * 校验手机号码是否唯一
   *
   * @param user 用户信息
   * @return
   */
  @Override
  public boolean checkPhoneUnique(SysUser user) {
    String phonenumber = user.getPhonenumber();
    if(StringUtils.isBlank(phonenumber)){
      return false;
    }

    long userId = user.getUserId() == null ? -1L : user.getUserId();
    SysUser sysUserQuerier = new SysUser();
    sysUserQuerier.setPhonenumber(phonenumber);
    SysUser info = super.getOne(new QueryWrapper<>(sysUserQuerier));
    return info != null && info.getUserId() != userId;
  }

  /**
   * 校验email是否唯一
   *
   * @param user 用户信息
   * @return
   */
  @Override
  public boolean checkEmailUnique(SysUser user) {
    String email = user.getEmail();
    if(StringUtils.isBlank(email)){
      return false;
    }

    long userId = user.getUserId() == null ? -1L : user.getUserId();
    SysUser sysUserQuerier = new SysUser();
    sysUserQuerier.setEmail(email);
    SysUser info = super.getOne(new QueryWrapper<>(sysUserQuerier));
    return info != null && info.getUserId() != userId;
  }

  /**
   * 校验用户是否允许操作
   *
   * @param user 用户信息
   */
  @Override
  public void checkAllowed(SysUser user) {
    if (user.getUserId() != null && user.isAdmin()) {
      throw new BusinessException("不允许操作超级管理员用户");
    }
  }

  /**
   * 查询用户所属角色组
   *
   * @param userId 用户ID
   * @return 结果
   */
  @Override
  public String getUserRoleGroup(Long userId) {
    List<SysRole> list = roleMapper.listByUserId(userId);
    StringBuilder idsStr = new StringBuilder();
    for (SysRole role : list) {
      idsStr.append(role.getRoleName()).append(",");
    }
    if (StringUtils.isNotEmpty(idsStr.toString())) {
      return idsStr.substring(0, idsStr.length() - 1);
    }
    return idsStr.toString();
  }

  /**
   * 查询用户所属岗位组
   *
   * @param userId 用户ID
   * @return 结果
   */
  @Override
  public String getUserPostGroup(Long userId) {
    List<SysPost> list = postMapper.listByUser(userId);
    StringBuilder idsStr = new StringBuilder();
    for (SysPost post : list) {
      idsStr.append(post.getPostName()).append(",");
    }
    if (StringUtils.isNotEmpty(idsStr.toString())) {
      return idsStr.substring(0, idsStr.length() - 1);
    }
    return idsStr.toString();
  }

  /**
   * 导入用户数据
   *
   * @param userList        用户数据列表
   * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
   * @param operName        操作用户
   * @return 结果
   */
  @Override
  public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
    if (userList == null || userList.size() == 0) {
      throw new BusinessException("导入用户数据不能为空！");
    }
    int successNum = 0;
    int failureNum = 0;
    StringBuilder successMsg = new StringBuilder();
    StringBuilder failureMsg = new StringBuilder();
    String password = configService.getValueByKey("sys.user.initPassword");
    for (SysUser user : userList) {
      try {
        // 验证是否存在这个用户
        SysUser u = userMapper.getByLoginName(user.getLoginName());
        if (u == null) {
          user.setPassword(Md5Utils.hash(user.getLoginName() + password));
          user.setCreateBy(operName);
          this.insert(user);
          successNum++;
          successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getLoginName()).append(" 导入成功");
        } else if (isUpdateSupport) {
          user.setUpdateBy(operName);
          this.update(user);
          successNum++;
          successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getLoginName()).append(" 更新成功");
        } else {
          failureNum++;
          failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(user.getLoginName()).append(" 已存在");
        }
      } catch (Exception e) {
        failureNum++;
        String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";
        failureMsg.append(msg).append(e.getMessage());
        log.error(msg, e);
      }
    }
    if (failureNum > 0) {
      failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
      throw new BusinessException(failureMsg.toString());
    } else {
      successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
    }
    return successMsg.toString();
  }

  /**
   * 用户状态修改
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public int changeStatus(SysUser user) {
    SysUser sysUser = new SysUser();
    sysUser.setUserId(user.getUserId());
    sysUser.setStatus(user.getStatus());
    return userMapper.updateById(sysUser);
  }
}
