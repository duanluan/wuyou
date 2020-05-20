package com.wuyou.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.system.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author wuyou
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
  /**
   * 分页列出用户
   *
   * @param page 分页对象
   * @param user 用户
   * @return
   */
  List<SysUser> page(Page<SysUser> page, @Param("user") SysUser user);

  /**
   * 分页列出已分配角色用户
   *
   * @param page 分页对象
   * @param user 用户
   * @return
   */
  List<SysUser> pageByAllocated(Page<SysUser> page, @Param("user") SysUser user);

  /**
   * 根据条件分页查询未已配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  List<SysUser> listAllocated(SysUser user);

  /**
   * 根据条件分页查询未分配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  List<SysUser> listUnallocated(SysUser user);

  /**
   * 通过用户名查询用户
   *
   * @param userName 用户名
   * @return 用户对象信息
   */
  SysUser getByLoginName(String userName);

  /**
   * 通过手机号码查询用户
   *
   * @param phoneNumber 手机号码
   * @return 用户对象信息
   */
  SysUser getByPhoneNumber(String phoneNumber);

  /**
   * 通过邮箱查询用户
   *
   * @param email 邮箱
   * @return 用户对象信息
   */
  SysUser getByEmail(String email);

  /**
   * 通过用户ID查询用户
   *
   * @param userId 用户ID
   * @return 用户对象信息
   */
  SysUser getById(Long userId);
}
