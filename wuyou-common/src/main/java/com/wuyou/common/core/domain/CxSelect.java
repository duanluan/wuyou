package com.wuyou.common.core.domain;

import lombok.Data;

import java.util.List;

/**
 * CxSelect树结构实体类
 *
 * @author wuyou
 */
@Data
public class CxSelect {

  private static final long serialVersionUID = 1L;

  /**
   * 数据值字段名称
   */
  private String v;

  /**
   * 数据标题字段名称
   */
  private String n;

  /**
   * 子集数据字段名称
   */
  private List<CxSelect> s;
}
