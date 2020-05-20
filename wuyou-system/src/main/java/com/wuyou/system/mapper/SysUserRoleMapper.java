package com.wuyou.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyou.system.domain.SysUserRole;
import org.apache.ibatis.annotations.Param;

/**
 * 用户与角色关联表 数据层
 *
 * @author wuyou
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
  /**
   * 批量取消授权用户角色
   *
   * @param roleId  角色ID
   * @param userIds 需要删除的用户数据ID
   * @return 结果
   */
  int deleteUserRoles(@Param("roleId") Long roleId, @Param("userIds") String[] userIds);
}
