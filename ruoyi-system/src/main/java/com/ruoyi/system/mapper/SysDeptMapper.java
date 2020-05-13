package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理 数据层
 *
 * @author ruoyi
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {
  /**
   * 分页列出部门
   *
   * @param page    分页对象
   * @param dept 部门
   * @return
   */
  List<SysDept> page(Page<SysDept> page, @Param("dept") SysDept dept);

  /**
   * 修改子元素关系
   *
   * @param depts 子元素
   * @return 结果
   */
  int updateChildren(@Param("depts") List<SysDept> depts);

  /**
   * 根据部门ID查询信息
   *
   * @param deptId 部门ID
   * @return 部门信息
   */
  SysDept getById(Long deptId);

  /**
   * 根据角色ID查询部门
   *
   * @param roleId 角色ID
   * @return 部门列表
   */
  List<String> listRoleDeptTree(Long roleId);

  /**
   * 修改所在部门的父级部门状态
   *
   * @param dept 部门
   */
  void updateStatus(SysDept dept);

  /**
   * 根据 ID 查询所有子部门
   *
   * @param deptId 部门ID
   * @return 部门列表
   */
  List<SysDept> listChildrenById(Long deptId);

  /**
   * 根据 ID 查询所有子部门（正常状态）
   *
   * @param deptId 部门 ID
   * @return 子部门数
   */
  int countNormalChildrenById(Long deptId);
}
