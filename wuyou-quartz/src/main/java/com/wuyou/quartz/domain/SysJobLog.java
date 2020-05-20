package com.wuyou.quartz.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wuyou.common.annotation.Excel;
import com.wuyou.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 定时任务调度日志表 sys_job_log
 *
 * @author wuyou
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysJobLog extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Excel(name = "日志序号")
  @TableId
  private Long jobLogId;
  /**
   * 任务名称
   */
  @Excel(name = "任务名称")
  private String jobName;
  /**
   * 任务组名
   */
  @Excel(name = "任务组名")
  private String jobGroup;
  /**
   * 调用目标字符串
   */
  @Excel(name = "调用目标字符串")
  private String invokeTarget;
  /**
   * 日志信息
   */
  @Excel(name = "日志信息")
  private String jobMessage;
  /**
   * 执行状态（0正常 1失败）
   */
  @Excel(name = "执行状态", readConverterExp = "0=正常,1=失败")
  private Integer status;
  /**
   * 异常信息
   */
  @Excel(name = "异常信息")
  private String exceptionInfo;
  /**
   * 开始时间
   */
  @TableField(exist = false)
  private Date startTime;
  /**
   * 结束时间
   */
  @TableField(exist = false)
  private Date endTime;
}
