package com.ruoyi.quartz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.quartz.domain.SysJobLog;

import java.util.List;

/**
 * 定时任务调度日志信息信息 服务层
 *
 * @author ruoyi
 */
public interface ISysJobLogService extends IService<SysJobLog> {
  /**
   * 分页列出任务日志
   *
   * @param page   分页对象
   * @param jobLog 任务日志
   * @return
   */
  IPage<SysJobLog> page(Page<SysJobLog> page, SysJobLog jobLog);

  /**
   * 获取quartz调度器日志的计划任务
   *
   * @param jobLog 调度日志信息
   * @return 调度任务日志集合
   */
  List<SysJobLog> list(SysJobLog jobLog);

  /**
   * 清空任务日志
   */
  void cleanJobLog();

  /**
   * 批量删除
   *
   * @param ids 任务日志 ids
   * @return
   */
  boolean removeByIds(String ids);
}
