package com.wuyou.system.enums;

/**
 * 部门状态
 *
 * @author duanluan
 */
public enum SysDeptStatus {
  /**
   * 正常
   */
  NORMAL(0),
  /**
   * 停用
   */
  DISABLE(1),
  ;

  private final Integer value;

  SysDeptStatus(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
