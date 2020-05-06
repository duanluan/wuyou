package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysUserOnline;
import com.ruoyi.system.mapper.SysUserOnlineMapper;
import com.ruoyi.system.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 在线用户 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SysUserOnlineServiceImpl extends ServiceImpl<SysUserOnlineMapper, SysUserOnline> implements ISysUserOnlineService {

  @Autowired
  private SysUserOnlineMapper userOnlineMapper;

  /**
   * 通过会话序号删除信息
   *
   * @param sessionId 会话ID
   * @return 在线用户信息
   */
  @Override
  public void deleteById(String sessionId) {
    SysUserOnline userOnline = getById(sessionId);
    if (userOnline != null) {
      userOnlineMapper.deleteById(sessionId);
    }
  }

  /**
   * 通过会话序号删除信息
   *
   * @param sessions 会话ID集合
   * @return 在线用户信息
   */
  @Override
  public void batchDeleteOnline(List<String> sessions) {
    for (String sessionId : sessions) {
      SysUserOnline userOnline = getById(sessionId);
      if (userOnline != null) {
        userOnlineMapper.deleteById(sessionId);
      }
    }
  }

  @Override
  public IPage<SysUserOnline> page(Page<SysUserOnline> page, SysUserOnline userOnline) {
    return page.setRecords(userOnlineMapper.page(page, userOnline));
  }

  /**
   * 查询会话集合
   *
   * @param userOnline 在线用户
   */
  @Override
  public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline) {
    return userOnlineMapper.page(null, userOnline);
  }

  /**
   * 强退用户
   *
   * @param sessionId 会话ID
   */
  @Override
  public void forceLogout(String sessionId) {
    userOnlineMapper.deleteById(sessionId);
  }

  /**
   * 查询会话集合
   *
   * @param expiredDate 失效日期
   */
  @Override
  public List<SysUserOnline> selectOnlineByExpired(Date expiredDate) {
    String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
    return userOnlineMapper.selectOnlineByExpired(lastAccessTime);
  }

  @Override
  public boolean saveOnline(SysUserOnline userOnline) {
    return userOnlineMapper.saveOnline(userOnline) > 0;
  }
}
