package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.system.domain.SysMenu;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysMenuMapper;
import com.ruoyi.system.service.ISysMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * 菜单 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

  public static final String PREMISSION_STRING = "perms[\"{0}\"]";

  @Autowired
  private SysMenuMapper menuMapper;

  /**
   * 根据用户查询菜单
   *
   * @param user 用户信息
   * @return 菜单列表
   */
  @Override
  public List<SysMenu> listByUser(SysUser user) {
    List<SysMenu> menus = new LinkedList<>();
    // 管理员显示所有菜单信息
    if (user.isAdmin()) {
      menus = menuMapper.listNormalAll();
    } else {
      menus = menuMapper.listNormalByUser(user.getUserId());
    }
    return listChildrenPerms(menus, 0);
  }

  /**
   * 查询菜单集合
   *
   * @return 所有菜单信息
   */
  @Override
  public List<SysMenu> list(SysMenu menu, Long userId) {
    List<SysMenu> menuList;
    if (SysUser.isAdmin(userId)) {
      menuList = menuMapper.list(menu);
    } else {
      menu.getParams().put("userId", userId);
      menuList = menuMapper.listByUser(menu);
    }
    return menuList;
  }

  /**
   * 查询菜单集合
   *
   * @return 所有菜单信息
   */
  @Override
  public List<SysMenu> list(Long userId) {
    List<SysMenu> menuList;
    if (SysUser.isAdmin(userId)) {
      menuList = super.list(new QueryWrapper<SysMenu>().orderByAsc("parent_id", "order_num"));
    } else {
      menuList = menuMapper.listAllByUser(userId);
    }
    return menuList;
  }

  /**
   * 根据用户ID查询权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  @Override
  public Set<String> listPerms(Long userId) {
    List<String> perms = menuMapper.listPermsByUser(userId);
    Set<String> permsSet = new HashSet<>();
    for (String perm : perms) {
      if (StringUtils.isNotEmpty(perm)) {
        permsSet.addAll(Arrays.asList(perm.trim().split(",")));
      }
    }
    return permsSet;
  }

  /**
   * 根据角色ID查询菜单
   *
   * @param role 角色对象
   * @return 菜单列表
   */
  @Override
  public List<Ztree> listRoleMenuTree(SysRole role, Long userId) {
    Long roleId = role.getRoleId();
    List<Ztree> ztrees;
    List<SysMenu> menuList = list(userId);
    if (roleId != null) {
      List<String> roleMenuList = menuMapper.listTree(roleId);
      ztrees = initZtree(menuList, roleMenuList, true);
    } else {
      ztrees = initZtree(menuList, null, true);
    }
    return ztrees;
  }

  /**
   * 查询所有菜单
   *
   * @return 菜单列表
   */
  @Override
  public List<Ztree> listMenuTree(Long userId) {
    List<SysMenu> menuList = list(userId);
    return initZtree(menuList);
  }

  /**
   * 查询系统所有权限
   *
   * @return 权限列表
   */
  @Override
  public LinkedHashMap<String, String> listUrlAndPerms(Long userId) {
    LinkedHashMap<String, String> section = new LinkedHashMap<>();
    List<SysMenu> permissions = list(userId);
    if (!CollectionUtils.sizeIsEmpty(permissions)) {
      for (SysMenu menu : permissions) {
        section.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getPerms()));
      }
    }
    return section;
  }

  /**
   * 对象转菜单树
   *
   * @param menuList 菜单列表
   * @return 树结构列表
   */
  public List<Ztree> initZtree(List<SysMenu> menuList) {
    return initZtree(menuList, null, false);
  }

  /**
   * 对象转菜单树
   *
   * @param menuList     菜单列表
   * @param roleMenuList 角色已存在菜单列表
   * @param permsFlag    是否需要显示权限标识
   * @return 树结构列表
   */
  public List<Ztree> initZtree(List<SysMenu> menuList, List<String> roleMenuList, boolean permsFlag) {
    List<Ztree> ztrees = new ArrayList<>();
    boolean isCheck = roleMenuList != null;
    for (SysMenu menu : menuList) {
      Ztree ztree = new Ztree();
      ztree.setId(menu.getMenuId());
      ztree.setpId(menu.getParentId());
      ztree.setName(transName(menu, permsFlag));
      ztree.setTitle(menu.getMenuName());
      if (isCheck) {
        ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
      }
      ztrees.add(ztree);
    }
    return ztrees;
  }

  public String transName(SysMenu menu, boolean permsFlag) {
    StringBuilder sb = new StringBuilder();
    sb.append(menu.getMenuName());
    if (permsFlag) {
      sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;").append(menu.getPerms()).append("</font>");
    }
    return sb.toString();
  }

  /**
   * 根据菜单ID查询信息
   *
   * @param menuId 菜单ID
   * @return 菜单信息
   */
  @Override
  public SysMenu getById(Long menuId) {
    return menuMapper.getById(menuId);
  }

  /**
   * 校验菜单名称是否唯一
   *
   * @param menu 菜单信息
   * @return 结果
   */
  @Override
  public boolean checkNameUnique(SysMenu menu) {
    long menuId = menu.getMenuId() == null ? -1L : menu.getMenuId();

    SysMenu sysMenuQuerier = new SysMenu();
    sysMenuQuerier.setMenuName(menu.getMenuName());
    sysMenuQuerier.setParentId(menu.getParentId());
    SysMenu info = super.getOne(new QueryWrapper<>(sysMenuQuerier));
    return info != null && info.getMenuId() != menuId;
  }

  /**
   * 根据父节点的ID获取所有子节点
   *
   * @param list     分类表
   * @param parentId 传入的父节点ID
   * @return String
   */
  public List<SysMenu> listChildrenPerms(List<SysMenu> list, int parentId) {
    List<SysMenu> returnList = new ArrayList<>();
    for (SysMenu sysMenu : list) {
      // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
      if (sysMenu.getParentId() == parentId) {
        recursionFn(list, sysMenu);
        returnList.add(sysMenu);
      }
    }
    return returnList;
  }

  /**
   * 递归列表
   *
   * @param list
   * @param t
   */
  private void recursionFn(List<SysMenu> list, SysMenu t) {
    // 得到子节点列表
    List<SysMenu> childList = listChildren(list, t);
    t.setChildren(childList);
    for (SysMenu tChild : childList) {
      if (hasChild(list, tChild)) {
        // 判断是否有子节点
        for (SysMenu n : childList) {
          recursionFn(list, n);
        }
      }
    }
  }

  /**
   * 得到子节点列表
   */
  private List<SysMenu> listChildren(List<SysMenu> list, SysMenu t) {
    List<SysMenu> tlist = new ArrayList<>();
    for (SysMenu n : list) {
      if (n.getParentId().longValue() == t.getMenuId().longValue()) {
        tlist.add(n);
      }
    }
    return tlist;
  }

  /**
   * 判断是否有子节点
   */
  private boolean hasChild(List<SysMenu> list, SysMenu t) {
    return listChildren(list, t).size() > 0;
  }
}
