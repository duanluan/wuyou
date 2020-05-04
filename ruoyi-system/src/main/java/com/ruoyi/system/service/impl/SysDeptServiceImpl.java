package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.enums.SysDeptStatus;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门管理 服务实现
 *
 * @author ruoyi
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

  @Autowired
  private SysDeptMapper deptMapper;

  @Override
  public IPage<SysDept> page(Page<SysDept> page, SysDept sysDept) {
    return page.setRecords(deptMapper.page(page, sysDept));
  }

  /**
   * 查询部门管理数据
   *
   * @param dept 部门信息
   * @return 部门信息集合
   */
  @DataScope()
  @Override
  public List<SysDept> list(SysDept dept) {
    // return super.list(new QueryWrapper<>(dept).apply(false, dept.getParams().get(Constants.DATA_SCOPE).toString()).orderByAsc("parent_id", "order_num"));
    return deptMapper.page(null, dept);
  }

  /**
   * 查询部门管理树
   *
   * @param dept 部门信息
   * @return 所有部门信息
   */
  @Override
  @DataScope
  public List<Ztree> listTree(SysDept dept) {
    return initZtree(list(dept));
  }

  /**
   * 根据角色ID查询部门（数据权限）
   *
   * @param role 角色对象
   * @return 部门列表（数据权限）
   */
  @Override
  public List<Ztree> listRoleDeptTree(SysRole role) {
    Long roleId = role.getRoleId();
    List<Ztree> ztrees;
    List<SysDept> deptList = list(new SysDept());
    if (roleId != null) {
      List<String> roleDeptList = deptMapper.listRoleDeptTree(roleId);
      ztrees = initZtree(deptList, roleDeptList);
    } else {
      ztrees = initZtree(deptList);
    }
    return ztrees;
  }

  /**
   * 对象转部门树
   *
   * @param deptList 部门列表
   * @return 树结构列表
   */
  public List<Ztree> initZtree(List<SysDept> deptList) {
    return initZtree(deptList, null);
  }

  /**
   * 对象转部门树
   *
   * @param deptList     部门列表
   * @param roleDeptList 角色已存在菜单列表
   * @return 树结构列表
   */
  public List<Ztree> initZtree(List<SysDept> deptList, List<String> roleDeptList) {
    List<Ztree> ztrees = new ArrayList<>();
    boolean isCheck = roleDeptList != null;
    for (SysDept dept : deptList) {
      if (SysDeptStatus.NORMAL.getValue().equals(dept.getStatus())) {
        Ztree ztree = new Ztree();
        ztree.setId(dept.getDeptId());
        ztree.setpId(dept.getParentId());
        ztree.setName(dept.getDeptName());
        ztree.setTitle(dept.getDeptName());
        if (isCheck) {
          ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
        }
        ztrees.add(ztree);
      }
    }
    return ztrees;
  }

  /**
   * 新增保存部门信息
   *
   * @param dept 部门信息
   * @return 结果
   */
  @Override
  public boolean save(SysDept dept) {
    SysDept info = deptMapper.getById(dept.getParentId());
    // 如果父节点不为"正常"状态,则不允许新增子节点
    if (SysDeptStatus.DISABLE.getValue().equals(info.getStatus())) {
      throw new BusinessException("部门停用，不允许新增");
    }
    dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
    return super.save(dept);
  }

  /**
   * 修改保存部门信息
   *
   * @param dept 部门信息
   * @return 结果
   */
  @Transactional
  @Override
  public boolean update(SysDept dept) {
    SysDept newParentDept = deptMapper.getById(dept.getParentId());
    SysDept oldDept = getById(dept.getDeptId());
    if (newParentDept != null && oldDept != null) {
      String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
      String oldAncestors = oldDept.getAncestors();
      dept.setAncestors(newAncestors);
      updateChildren(dept.getDeptId(), newAncestors, oldAncestors);
    }
    boolean result = super.updateById(dept);
    if (SysDeptStatus.NORMAL.getValue().equals(dept.getStatus())) {
      // 如果该部门是启用状态，则启用该部门的所有上级部门
      updateParentStatus(dept);
    }
    return result;
  }

  /**
   * 修改该部门的父级部门状态
   *
   * @param dept 当前部门
   */
  private void updateParentStatus(SysDept dept) {
    String updateBy = dept.getUpdateBy();
    dept = deptMapper.getById(dept.getDeptId());
    dept.setUpdateBy(updateBy);
    deptMapper.updateStatus(dept);
  }

  /**
   * 修改子元素关系
   *
   * @param deptId       被修改的部门ID
   * @param newAncestors 新的父ID集合
   * @param oldAncestors 旧的父ID集合
   */
  public void updateChildren(Long deptId, String newAncestors, String oldAncestors) {
    List<SysDept> children = deptMapper.listChildrenById(deptId);
    for (SysDept child : children) {
      child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
    }
    if (children.size() > 0) {
      deptMapper.updateChildren(children);
    }
  }

  /**
   * 根据部门ID查询信息
   *
   * @param deptId 部门ID
   * @return 部门信息
   */
  @Override
  public SysDept getById(Long deptId) {
    return deptMapper.getById(deptId);
  }

  /**
   * 校验部门名称是否唯一
   *
   * @param dept 部门信息
   * @return 结果
   */
  @Override
  public boolean checkNameUnique(SysDept dept) {
    long deptId = dept.getDeptId() == null ? -1L : dept.getDeptId();
    SysDept sysDeptQuerier = new SysDept();
    sysDeptQuerier.setDeptName(dept.getDeptName());
    sysDeptQuerier.setParentId(dept.getParentId());
    SysDept info = super.getOne(new QueryWrapper<>(sysDeptQuerier));
    return info != null && info.getDeptId() != deptId;
  }
}
