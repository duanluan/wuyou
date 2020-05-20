package com.wuyou.quartz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.quartz.domain.SysJobLog;
import com.wuyou.quartz.mapper.SysJobLogMapper;
import com.wuyou.quartz.service.ISysJobLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 定时任务调度日志信息 服务层
 *
 * @author wuyou
 */
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements ISysJobLogService {

  @Autowired
  private SysJobLogMapper jobLogMapper;

  @Override
  public IPage<SysJobLog> page(Page<SysJobLog> page, SysJobLog jobLog) {
    return page.setRecords(jobLogMapper.page(page, jobLog));
  }

  /**
   * 获取quartz调度器日志的计划任务
   *
   * @param jobLog 调度日志信息
   * @return 调度任务日志集合
   */
  @Override
  public List<SysJobLog> list(SysJobLog jobLog) {
    return jobLogMapper.page(null, jobLog);
  }

  /**
   * 清空任务日志
   */
  @Override
  public void cleanJobLog() {
    jobLogMapper.cleanJobLog();
  }

  @Override
  public boolean removeByIds(String ids) {
    return super.removeByIds(Arrays.asList(StringUtils.split(ids, ",")));
  }
}
