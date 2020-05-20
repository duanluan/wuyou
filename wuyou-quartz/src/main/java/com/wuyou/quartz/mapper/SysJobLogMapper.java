package com.wuyou.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.quartz.domain.SysJobLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 调度任务日志信息 数据层
 *
 * @author wuyou
 */
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {

  /**
   * 分页获取quartz调度器日志的计划任务
   *
   * @param page   分页对象
   * @param jobLog 调度日志信息
   * @return 调度任务日志集合
   */
  List<SysJobLog> page(Page<SysJobLog> page, @Param("jobLog") SysJobLog jobLog);

  /**
   * 清空任务日志
   */
  void cleanJobLog();


}
