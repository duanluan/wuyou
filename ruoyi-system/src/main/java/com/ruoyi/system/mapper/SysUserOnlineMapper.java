package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.SysUserOnline;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 在线用户 数据层
 *
 * @author ruoyi
 */
public interface SysUserOnlineMapper extends BaseMapper<SysUserOnline> {
  /**
   * 分页列出在线用户
   *
   * @param page       分页对象
   * @param userOnline 在线用户
   * @return
   */
  List<SysUserOnline> page(Page<SysUserOnline> page, @Param("userOnline") SysUserOnline userOnline);

  /**
   * 查询过期会话集合
   *
   * @param lastAccessTime 过期时间
   * @return 会话集合
   */
  List<SysUserOnline> selectOnlineByExpired(String lastAccessTime);

}
