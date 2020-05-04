package com.ruoyi.system.enums;

/**
 * 字典类型状态
 *
 * @author duanluan
 */
public enum SysDictTypeStatus {
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

  SysDictTypeStatus(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
