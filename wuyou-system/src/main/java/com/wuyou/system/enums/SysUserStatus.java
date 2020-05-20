package com.wuyou.system.enums;

/**
 * 用户状态
 *
 * @author wuyou
 */
public enum SysUserStatus {
  /**
   * 正常
   */
  NORMAL(0),
  /**
   * 停用
   */
  DISABLE(1),
  /**
   * 删除
   */
  DELETED(2),
  ;

  private final Integer value;

  SysUserStatus(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
