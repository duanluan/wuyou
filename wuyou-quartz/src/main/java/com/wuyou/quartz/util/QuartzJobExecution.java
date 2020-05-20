package com.wuyou.quartz.util;

import com.wuyou.quartz.domain.SysJob;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 *
 * @author wuyou
 */
public class QuartzJobExecution extends AbstractQuartzJob {

  @Override
  protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
    JobInvokeUtil.invokeMethod(sysJob);
  }
}
