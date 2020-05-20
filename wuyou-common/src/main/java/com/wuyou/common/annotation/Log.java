package com.wuyou.common.annotation;

import com.wuyou.common.enums.BusinessType;
import com.wuyou.common.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 * @author wuyou
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface Log {
  /**
   * 模块
   */
  String title() default "";

  /**
   * 功能
   */
  BusinessType businessType() default BusinessType.OTHER;

  /**
   * 操作人类别
   */
  OperatorType operatorType() default OperatorType.MANAGE;

  /**
   * 是否保存请求的参数
   */
  boolean isSaveRequestData() default true;
}
