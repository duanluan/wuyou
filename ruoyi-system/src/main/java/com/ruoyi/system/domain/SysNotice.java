package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 通知公告表 sys_notice
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysNotice extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 公告ID
   */
  @TableId
  private Long noticeId;
  /**
   * 公告标题
   */
  @Size(max = 50, message = "公告标题不能超过50个字符")
  @NotBlank(message = "公告标题不能为空")
  private String noticeTitle;
  /**
   * 公告类型（1通知 2公告）
   */
  private Integer noticeType;
  /**
   * 公告内容
   */
  private String noticeContent;
  /**
   * 公告状态（0正常 1关闭）
   */
  private Integer status;
}
