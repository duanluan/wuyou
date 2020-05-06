package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysUserOnline;

import java.util.Date;
import java.util.List;

/**
 * 在线用户 服务层
 *
 * @author ruoyi
 */
public interface ISysUserOnlineService extends IService<SysUserOnline> {
  /**
   * 通过会话序号删除信息
   *
   * @param sessionId 会话ID
   * @return 在线用户信息
   */
  void deleteById(String sessionId);

  /**
   * 通过会话序号删除信息
   *
   * @param sessions 会话ID集合
   * @return 在线用户信息
   */
  void batchDeleteOnline(List<String> sessions);

  /**
   * 分页列出在线用户
   *
   * @param page       分页对象
   * @param userOnline 在线用户
   * @return
   */
  IPage<SysUserOnline> page(Page<SysUserOnline> page, SysUserOnline userOnline);

  /**
   * 查询会话集合
   *
   * @param userOnline 分页参数
   * @return 会话集合
   */
  List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline);

  /**
   * 强退用户
   *
   * @param sessionId 会话ID
   */
  void forceLogout(String sessionId);

  /**
   * 查询会话集合
   *
   * @param expiredDate 有效期
   * @return 会话集合
   */
  List<SysUserOnline> selectOnlineByExpired(Date expiredDate);

  boolean saveOnline(SysUserOnline userOnline);
}
