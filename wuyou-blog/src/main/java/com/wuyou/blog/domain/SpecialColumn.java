package com.wuyou.blog.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpecialColumn {
  /**
   * ID
   */
  private Long id;
  /**
   * 名称
   */
  private String name;
  /**
   * 描述
   */
  private String desc;
  /**
   * 创建时间
   */
  private LocalDateTime createTime;
  /**
   * 创建人 ID
   */
  private Long createdBy;
  /**
   * 更新时间
   */
  private LocalDateTime updateTime;
  /**
   * 更新人 ID
   */
  private Long updatedBy;
  /**
   * 是否删除
   */
  private Boolean deleted;
  /**
   * 备注
   */
  private String remark;

}
