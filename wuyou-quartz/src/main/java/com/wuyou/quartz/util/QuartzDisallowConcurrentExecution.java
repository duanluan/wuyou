package com.wuyou.quartz.util;

import com.wuyou.quartz.domain.SysJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @author wuyou
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {

  @Override
  protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
    JobInvokeUtil.invokeMethod(sysJob);
  }
}
