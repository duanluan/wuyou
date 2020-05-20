package com.wuyou.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.system.domain.SysLogininfor;
import com.wuyou.system.mapper.SysLogininforMapper;
import com.wuyou.system.service.ISysLogininforService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author wuyou
 */
@Service
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor> implements ISysLogininforService {

  @Autowired
  private SysLogininforMapper logininforMapper;

  @Override
  public IPage<SysLogininfor> page(Page<SysLogininfor> page, SysLogininfor logininfor) {
    return page.setRecords(logininforMapper.page(page, logininfor));
  }

  /**
   * 查询系统登录日志集合
   *
   * @param logininfor 访问日志对象
   * @return 登录记录集合
   */
  @Override
  public List<SysLogininfor> list(SysLogininfor logininfor) {
    return logininforMapper.page(null, logininfor);
  }

  /**
   * 批量删除系统登录日志
   *
   * @param ids 需要删除的数据
   * @return
   */
  @Override
  public boolean removeByIds(String ids) {
    return super.removeByIds(Arrays.asList(StringUtils.split(ids, ",")));
  }

  /**
   * 清空系统登录日志
   */
  @Override
  public void cleanLogininfor() {
    logininforMapper.cleanLogininfor();
  }
}
