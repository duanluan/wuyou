package com.wuyou.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyou.system.domain.SysMenu;

import java.util.List;

/**
 * 菜单表 数据层
 *
 * @author wuyou
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
  /**
   * 根据用户ID查询菜单
   *
   * @param userId 用户ID
   * @return 菜单列表
   */
  List<SysMenu> listAllByUser(Long userId);

  /**
   * 查询系统正常显示菜单（不含按钮）
   *
   * @return 菜单列表
   */
  List<SysMenu> listNormalAll();

  /**
   * 根据用户ID查询菜单
   *
   * @param userId 用户ID
   * @return 菜单列表
   */
  List<SysMenu> listNormalByUser(Long userId);

  /**
   * 根据用户ID查询权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  List<String> listPermsByUser(Long userId);

  /**
   * 根据角色ID查询菜单
   *
   * @param roleId 角色ID
   * @return 菜单列表
   */
  List<String> listTree(Long roleId);

  /**
   * 查询系统菜单列表
   *
   * @param menu 菜单信息
   * @return 菜单列表
   */
  List<SysMenu> list(SysMenu menu);

  /**
   * 查询系统菜单列表
   *
   * @param menu 菜单信息
   * @return 菜单列表
   */
  List<SysMenu> listByUser(SysMenu menu);

  /**
   * 根据菜单ID查询信息
   *
   * @param menuId 菜单ID
   * @return 菜单信息
   */
  SysMenu getById(Long menuId);
}
