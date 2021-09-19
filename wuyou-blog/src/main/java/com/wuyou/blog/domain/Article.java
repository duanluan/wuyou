package com.wuyou.blog.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章
 */
@Data
public class Article {

  private Long id;
  /**
   * 标题
   */
  private String title;
  /**
   * 内容
   */
  private String content;
  /**
   * 作者 ID
   */
  private Long authorId;
  /**
   * 摘要
   */
  private String abstractContent;

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
