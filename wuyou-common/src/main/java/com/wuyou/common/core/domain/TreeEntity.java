package com.wuyou.common.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Tree基类
 *
 * @author wuyou
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TreeEntity extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 父菜单名称
   */
  private String parentName;
  /**
   * 父菜单ID
   */
  private Long parentId;
  /**
   * 显示顺序
   */
  private Integer orderNum;
  /**
   * 祖级列表
   */
  private String ancestors;
}