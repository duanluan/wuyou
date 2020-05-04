package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysRole;

import java.util.List;

/**
 * 部门管理 服务层
 *
 * @author ruoyi
 */
public interface ISysDeptService extends IService<SysDept> {
  /**
   * 分页列出部门
   *
   * @param page    分页对象
   * @param sysDept 部门
   * @return
   */
  IPage<SysDept> page(Page<SysDept> page, SysDept sysDept);

  /**
   * 查询部门管理数据
   *
   * @param dept 部门信息
   * @return 部门信息集合
   */
  List<SysDept> list(SysDept dept);

  /**
   * 查询部门管理树
   *
   * @param dept 部门信息
   * @return 所有部门信息
   */
  List<Ztree> listTree(SysDept dept);

  /**
   * 根据角色ID查询菜单
   *
   * @param role 角色对象
   * @return 菜单列表
   */
  List<Ztree> listRoleDeptTree(SysRole role);

  /**
   * 新增保存部门信息
   *
   * @param dept 部门信息
   * @return 结果
   */
  @Override
  boolean save(SysDept dept);

  /**
   * 修改保存部门信息
   *
   * @param dept 部门信息
   * @return 结果
   */
  boolean update(SysDept dept);

  /**
   * 根据部门ID查询信息
   *
   * @param deptId 部门ID
   * @return 部门信息
   */
  SysDept getById(Long deptId);

  /**
   * 校验部门名称是否唯一
   *
   * @param dept 部门信息
   * @return 结果
   */
  boolean checkNameUnique(SysDept dept);
}
