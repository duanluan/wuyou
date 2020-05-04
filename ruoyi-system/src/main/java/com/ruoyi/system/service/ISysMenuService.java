package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.system.domain.SysMenu;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单 业务层
 *
 * @author ruoyi
 */
public interface ISysMenuService extends IService<SysMenu> {
  /**
   * 根据用户ID查询菜单
   *
   * @param user 用户信息
   * @return 菜单列表
   */
  List<SysMenu> listByUser(SysUser user);

  /**
   * 查询系统菜单列表
   *
   * @param menu   菜单信息
   * @param userId 用户ID
   * @return 菜单列表
   */
  List<SysMenu> list(SysMenu menu, Long userId);

  /**
   * 查询菜单集合
   *
   * @param userId 用户ID
   * @return 所有菜单信息
   */
  List<SysMenu> list(Long userId);

  /**
   * 根据用户ID查询权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  Set<String> listPerms(Long userId);

  /**
   * 根据角色ID查询菜单
   *
   * @param role   角色对象
   * @param userId 用户ID
   * @return 菜单列表
   */
  List<Ztree> listRoleMenuTree(SysRole role, Long userId);

  /**
   * 查询所有菜单信息
   *
   * @param userId 用户ID
   * @return 菜单列表
   */
  List<Ztree> listMenuTree(Long userId);

  /**
   * 查询系统所有权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  Map<String, String> listUrlAndPerms(Long userId);

  /**
   * 根据菜单ID查询信息
   *
   * @param menuId 菜单ID
   * @return 菜单信息
   */
  SysMenu getById(Long menuId);

  /**
   * 校验菜单名称是否唯一
   *
   * @param menu 菜单信息
   * @return 结果
   */
  boolean checkNameUnique(SysMenu menu);
}
