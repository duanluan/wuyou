package com.wuyou.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.system.domain.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表 数据层
 *
 * @author wuyou
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
  /**
   * 分页列出角色
   *
   * @param page 分页对象
   * @param role 角色
   * @return
   */
  List<SysRole> page(Page<SysRole> page, @Param("role") SysRole role);

  /**
   * 根据用户ID查询角色
   *
   * @param userId 用户ID
   * @return 角色列表
   */
  List<SysRole> listByUserId(Long userId);
}
