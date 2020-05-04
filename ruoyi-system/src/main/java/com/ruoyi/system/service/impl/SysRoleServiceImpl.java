package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysRoleDept;
import com.ruoyi.system.domain.SysRoleMenu;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysRoleDeptMapper;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysRoleDeptService;
import com.ruoyi.system.service.ISysRoleMenuService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

  @Autowired
  private SysRoleMapper roleMapper;
  @Autowired
  private SysUserRoleMapper userRoleMapper;
  @Autowired
  private SysRoleDeptMapper roleDeptMapper;

  @Autowired
  private ISysRoleDeptService roleDeptService;
  @Autowired
  private ISysRoleMenuService roleMenuService;
  @Autowired
  private ISysUserRoleService userRoleService;

  @Override
  public IPage<SysRole> page(Page<SysRole> page, SysRole role) {
    return page.setRecords(roleMapper.page(page, role));
  }

  /**
   * 根据条件分页查询角色数据
   *
   * @param role 角色信息
   * @return 角色数据集合信息
   */
  @DataScope
  @Override
  public List<SysRole> list(SysRole role) {
    return roleMapper.page(null, role);
  }

  /**
   * 根据用户ID查询权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  @Override
  public Set<String> listKeys(Long userId) {
    List<SysRole> perms = roleMapper.listByUserId(userId);
    Set<String> permsSet = new HashSet<>();
    for (SysRole perm : perms) {
      if (perm != null) {
        permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
      }
    }
    return permsSet;
  }

  /**
   * 根据用户ID查询角色
   *
   * @param userId 用户ID
   * @return 角色列表
   */
  @Override
  public List<SysRole> listByUserId(Long userId) {
    List<SysRole> userRoles = roleMapper.listByUserId(userId);
    List<SysRole> roles = listAll();
    for (SysRole role : roles) {
      for (SysRole userRole : userRoles) {
        if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
          role.setFlag(true);
          break;
        }
      }
    }
    return roles;
  }

  /**
   * 查询所有角色
   *
   * @return 角色列表
   */
  @Override
  public List<SysRole> listAll() {
    return SpringUtils.getAopProxy(this).list(new SysRole());
  }

  /**
   * 通过角色ID删除角色
   *
   * @param roleId 角色ID
   * @return 结果
   */
  @Override
  public boolean deleteById(Long roleId) {
    return roleMapper.deleteById(roleId) > 0;
  }

  /**
   * 批量删除角色信息
   *
   * @param ids 需要删除的数据ID
   * @return
   * @throws Exception
   */
  @Override
  public boolean deleteByIds(String ids) throws BusinessException {
    String[] roleIds = StringUtils.split(ids, ",");
    for (String roleId : roleIds) {
      checkAllowed(new SysRole(Long.valueOf(roleId)));
      SysRole role = getById(roleId);
      SysRole sysRoleQuerier = new SysRole();
      sysRoleQuerier.setRoleId(Long.valueOf(roleId));
      if (super.count(new QueryWrapper<>(sysRoleQuerier)) > 0) {
        throw new BusinessException(String.format("%1$s已分配,不能删除", role.getRoleName()));
      }
    }
    return super.removeByIds(Arrays.asList(StringUtils.split(ids, ",")));
  }

  /**
   * 新增保存角色信息
   *
   * @param role 角色信息
   * @return 结果
   */
  @Transactional
  @Override
  public boolean insert(SysRole role) {
    // 新增角色信息
    return super.save(role);
  }

  /**
   * 修改保存角色信息
   *
   * @param role 角色信息
   * @return 结果
   */
  @Transactional
  @Override
  public boolean update(SysRole role) {
    // 修改角色信息
    roleMapper.updateById(role);
    // 删除角色与菜单关联
    SysRoleMenu sysRoleMenuQuerier = new SysRoleMenu();
    sysRoleMenuQuerier.setRoleId(role.getRoleId());
    roleMenuService.remove(new QueryWrapper<>(sysRoleMenuQuerier));
    return super.save(role);
  }

  /**
   * 修改数据权限信息
   *
   * @param role 角色信息
   * @return 结果
   */
  @Transactional
  @Override
  public boolean authDataScope(SysRole role) {
    // 修改角色信息
    roleMapper.updateById(role);
    // 删除角色与部门关联
    SysRoleDept sysRoleDeptQuerier = new SysRoleDept();
    sysRoleDeptQuerier.setRoleId(role.getRoleId());
    roleDeptMapper.delete(new QueryWrapper<>(sysRoleDeptQuerier));
    // 新增角色和部门信息（数据权限）
    return insertRoleDept(role);
  }

  /**
   * 新增角色菜单信息
   *
   * @param role 角色对象
   * @return
   */
  public boolean insertRoleMenu(SysRole role) {
    // 新增用户与角色管理
    List<SysRoleMenu> list = new ArrayList<>();
    for (Long menuId : role.getMenuIds()) {
      SysRoleMenu rm = new SysRoleMenu();
      rm.setRoleId(role.getRoleId());
      rm.setMenuId(menuId);
      list.add(rm);
    }
    if (list.size() > 0) {
      return roleMenuService.saveBatch(list);
    }
    return false;
  }

  /**
   * 新增角色部门信息(数据权限)
   *
   * @param role 角色对象
   * @return
   */
  public boolean insertRoleDept(SysRole role) {
    // 新增角色与部门（数据权限）管理
    List<SysRoleDept> list = new ArrayList<>();
    for (Long deptId : role.getDeptIds()) {
      SysRoleDept rd = new SysRoleDept();
      rd.setRoleId(role.getRoleId());
      rd.setDeptId(deptId);
      list.add(rd);
    }
    if (list.size() > 0) {
      return roleDeptService.saveBatch(list);
    }
    return false;
  }

  /**
   * 校验角色名称是否唯一
   *
   * @param role 角色信息
   * @return 结果
   */
  @Override
  public boolean checkNameUnique(SysRole role) {
    long roleId = role.getRoleId() == null ? -1L : role.getRoleId();
    SysRole sysRoleQuerier = new SysRole();
    sysRoleQuerier.setRoleName(role.getRoleName());
    SysRole info = super.getOne(new QueryWrapper<>(sysRoleQuerier));
    return info != null && info.getRoleId() != roleId;
  }

  /**
   * 校验角色权限是否唯一
   *
   * @param role 角色信息
   * @return 结果
   */
  @Override
  public boolean checkKeyUnique(SysRole role) {
    long roleId = role.getRoleId() == null ? -1L : role.getRoleId();
    SysRole sysRoleQuerier = new SysRole();
    sysRoleQuerier.setRoleKey(role.getRoleKey());
    SysRole info = super.getOne(new QueryWrapper<>(sysRoleQuerier));
    return info != null && info.getRoleId() != roleId;
  }

  /**
   * 校验角色是否允许操作
   *
   * @param role 角色信息
   */
  @Override
  public void checkAllowed(SysRole role) {
    if (role.getRoleId() != null && role.isAdmin()) {
      throw new BusinessException("不允许操作超级管理员角色");
    }
  }

  /**
   * 角色状态修改
   *
   * @param role 角色信息
   * @return 结果
   */
  @Override
  public boolean changeStatus(SysRole role) {
    SysRole sysRole = new SysRole();
    sysRole.setRoleId(role.getRoleId());
    sysRole.setStatus(role.getStatus());
    return super.updateById(sysRole);
  }

  /**
   * 批量取消授权用户角色
   *
   * @param roleId  角色ID
   * @param userIds 需要删除的用户数据ID
   * @return 结果
   */
  @Override
  public int deleteAuthUsers(Long roleId, String userIds) {
    return userRoleMapper.deleteUserRoles(roleId, StringUtils.split(userIds, ","));
  }

  /**
   * 批量选择授权用户角色
   *
   * @param roleId  角色ID
   * @param userIds 需要删除的用户数据ID
   * @return 结果
   */
  @Override
  public boolean insertAuthUsers(Long roleId, String userIds) {
    String[] users = StringUtils.split(userIds, ",");
    // 新增用户与角色管理
    List<SysUserRole> list = new ArrayList<>();
    for (String userId : users) {
      SysUserRole ur = new SysUserRole();
      ur.setUserId(Long.valueOf(userId));
      ur.setRoleId(roleId);
      list.add(ur);
    }
    return userRoleService.saveBatch(list);
  }
}
