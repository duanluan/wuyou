package com.ruoyi.quartz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.quartz.domain.SysJob;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 定时任务调度信息信息 服务层
 *
 * @author ruoyi
 */
public interface ISysJobService extends IService<SysJob> {
  /**
   * 分页列出任务
   *
   * @param page 分页对象
   * @param job  任务
   * @return
   */
  IPage<SysJob> page(Page<SysJob> page, SysJob job);

  /**
   * 获取quartz调度器的计划任务
   *
   * @param job 调度信息
   * @return 调度任务集合
   */
  List<SysJob> list(SysJob job);

  /**
   * 暂停任务
   *
   * @param job 调度信息
   * @return 结果
   * @throws SchedulerException
   */
  boolean pauseJob(SysJob job) throws SchedulerException;

  /**
   * 恢复任务
   *
   * @param job 调度信息
   * @return 结果
   * @throws SchedulerException
   */
  boolean resumeJob(SysJob job) throws SchedulerException;

  /**
   * 删除任务后，所对应的trigger也将被删除
   *
   * @param job 调度信息
   * @return 结果
   * @throws SchedulerException
   */
  boolean deleteJob(SysJob job) throws SchedulerException;

  /**
   * 批量删除调度信息
   *
   * @param ids 需要删除的数据ID
   * @throws SchedulerException
   */
  void deleteJobByIds(String ids) throws SchedulerException;

  /**
   * 任务调度状态修改
   *
   * @param job 调度信息
   * @return 结果
   * @throws SchedulerException
   */
  boolean changeStatus(SysJob job) throws SchedulerException;

  /**
   * 立即运行任务
   *
   * @param job 调度信息
   * @throws SchedulerException
   */
  void run(SysJob job) throws SchedulerException;

  /**
   * 新增任务
   *
   * @param job 调度信息
   * @return 结果
   * @throws SchedulerException
   * @throws TaskException
   */
  boolean insertJob(SysJob job) throws SchedulerException, TaskException;

  /**
   * 更新任务
   *
   * @param job 调度信息
   * @return 结果
   * @throws SchedulerException
   * @throws TaskException
   */
  boolean updateJob(SysJob job) throws SchedulerException, TaskException;

  /**
   * 校验cron表达式是否有效
   *
   * @param cronExpression 表达式
   * @return 结果
   */
  boolean checkCronExpressionIsValid(String cronExpression);
}