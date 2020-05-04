package com.ruoyi.quartz.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.quartz.util.CronUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 定时任务调度表 sys_job
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysJob extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 任务ID
   */
  @Excel(name = "任务序号", cellType = ColumnType.NUMERIC)
  @TableId
  private Long jobId;
  /**
   * 任务名称
   */
  @Excel(name = "任务名称")
  @Size(min = 0, max = 64, message = "任务名称不能超过64个字符")
  @NotBlank(message = "任务名称不能为空")
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
  @Size(min = 0, max = 1000, message = "调用目标字符串长度不能超过500个字符")
  @NotBlank(message = "调用目标字符串不能为空")
  private String invokeTarget;
  /**
   * cron执行表达式
   */
  @Excel(name = "执行表达式 ")
  @Size(min = 0, max = 255, message = "Cron执行表达式不能超过255个字符")
  @NotBlank(message = "Cron执行表达式不能为空")
  private String cronExpression;
  /**
   * cron计划策略
   */
  @Excel(name = "计划策略 ", readConverterExp = "0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行")
  private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;
  /**
   * 是否并发执行（0允许 1禁止）
   */
  @Excel(name = "并发执行", readConverterExp = "0=允许,1=禁止")
  private String concurrent;
  /**
   * 任务状态（0正常 1暂停）
   */
  @Excel(name = "任务状态", readConverterExp = "0=正常,1=暂停")
  private String status;

  public Date getNextValidTime() {
    if (StringUtils.isNotEmpty(cronExpression)) {
      return CronUtils.getNextExecution(cronExpression);
    }
    return null;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
      .append("jobId", getJobId())
      .append("jobName", getJobName())
      .append("jobGroup", getJobGroup())
      .append("cronExpression", getCronExpression())
      .append("nextValidTime", getNextValidTime())
      .append("misfirePolicy", getMisfirePolicy())
      .append("concurrent", getConcurrent())
      .append("status", getStatus())
      .append("createBy", getCreateBy())
      .append("createTime", getCreateTime())
      .append("updateBy", getUpdateBy())
      .append("updateTime", getUpdateTime())
      .append("remark", getRemark())
      .toString();
  }
}