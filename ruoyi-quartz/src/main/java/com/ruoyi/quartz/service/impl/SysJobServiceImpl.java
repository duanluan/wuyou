package com.ruoyi.quartz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.mapper.SysJobMapper;
import com.ruoyi.quartz.service.ISysJobService;
import com.ruoyi.quartz.util.CronUtils;
import com.ruoyi.quartz.util.ScheduleUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 定时任务调度信息 服务层
 *
 * @author ruoyi
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements ISysJobService {

  @Autowired
  private Scheduler scheduler;
  @Autowired
  private SysJobMapper jobMapper;

  /**
   * 项目启动时，初始化定时器
   * 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
   */
  @PostConstruct
  public void init() throws SchedulerException, TaskException {
    scheduler.clear();
    List<SysJob> jobList = super.list();
    for (SysJob job : jobList) {
      ScheduleUtils.createScheduleJob(scheduler, job);
    }
  }

  @Override
  public IPage<SysJob> page(Page<SysJob> page, SysJob job) {
    return page.setRecords(jobMapper.page(page, job));
  }

  /**
   * 获取quartz调度器的计划任务列表
   *
   * @param job 调度信息
   * @return
   */
  @Override
  public List<SysJob> list(SysJob job) {
    return jobMapper.page(null, job);
  }

  /**
   * 暂停任务
   *
   * @param job 调度信息
   * @return
   */
  @Transactional
  @Override
  public boolean pauseJob(SysJob job) throws SchedulerException {
    Long jobId = job.getJobId();
    String jobGroup = job.getJobGroup();
    job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
    boolean result = super.updateById(job);
    if (result) {
      scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
    }
    return result;
  }

  /**
   * 恢复任务
   *
   * @param job 调度信息
   * @return
   */
  @Transactional
  @Override
  public boolean resumeJob(SysJob job) throws SchedulerException {
    Long jobId = job.getJobId();
    String jobGroup = job.getJobGroup();
    job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
    boolean result = super.updateById(job);
    if (result) {
      scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
    }
    return result;
  }

  /**
   * 删除任务后，所对应的trigger也将被删除
   *
   * @param job 调度信息
   * @return
   */
  @Transactional
  @Override
  public boolean deleteJob(SysJob job) throws SchedulerException {
    Long jobId = job.getJobId();
    String jobGroup = job.getJobGroup();
    boolean result = super.removeById(jobId);
    if (result) {
      scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
    }
    return result;
  }

  /**
   * 批量删除调度信息
   *
   * @param ids 需要删除的数据ID
   * @return 结果
   */
  @Transactional
  @Override
  public void deleteJobByIds(String ids) throws SchedulerException {
    String[] jobIds = StringUtils.split(ids,",");
    for (String jobId : jobIds) {
      SysJob job = super.getById(Long.valueOf(jobId));
      deleteJob(job);
    }
  }

  /**
   * 任务调度状态修改
   *
   * @param job 调度信息
   * @return
   */
  @Transactional
  @Override
  public boolean changeStatus(SysJob job) throws SchedulerException {
    boolean rows = false;
    String status = job.getStatus();
    if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
      rows = resumeJob(job);
    } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
      rows = pauseJob(job);
    }
    return rows;
  }

  /**
   * 立即运行任务
   *
   * @param job 调度信息
   */
  @Transactional
  @Override
  public void run(SysJob job) throws SchedulerException {
    Long jobId = job.getJobId();
    SysJob tmpObj = getById(job.getJobId());
    // 参数
    JobDataMap dataMap = new JobDataMap();
    dataMap.put(ScheduleConstants.TASK_PROPERTIES, tmpObj);
    scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, tmpObj.getJobGroup()), dataMap);
  }

  /**
   * 新增任务
   *
   * @param job 调度信息 调度信息
   * @return
   */
  @Transactional
  @Override
  public boolean insertJob(SysJob job) throws SchedulerException, TaskException {
    job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
    boolean result = super.save(job);
    if (result) {
      ScheduleUtils.createScheduleJob(scheduler, job);
    }
    return result;
  }

  /**
   * 更新任务的时间表达式
   *
   * @param job 调度信息
   * @return
   */
  @Transactional
  @Override
  public boolean updateJob(SysJob job) throws SchedulerException, TaskException {
    SysJob properties = getById(job.getJobId());
    boolean result = super.updateById(job);
    if (result) {
      updateSchedulerJob(job, properties.getJobGroup());
    }
    return result;
  }

  /**
   * 更新任务
   *
   * @param job      任务对象
   * @param jobGroup 任务组名
   */
  public void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException {
    Long jobId = job.getJobId();
    // 判断是否存在
    JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
    if (scheduler.checkExists(jobKey)) {
      // 防止创建时存在数据问题 先移除，然后在执行创建操作
      scheduler.deleteJob(jobKey);
    }
    ScheduleUtils.createScheduleJob(scheduler, job);
  }

  /**
   * 校验cron表达式是否有效
   *
   * @param cronExpression 表达式
   * @return 结果
   */
  @Override
  public boolean checkCronExpressionIsValid(String cronExpression) {
    return CronUtils.isValid(cronExpression);
  }
}