package com.ruoyi.common.annotation;

import com.ruoyi.common.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * 自定义多数据源切换注解
 *
 * @author ruoyi
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DataSource {
  /**
   * 切换数据源名称
   */
  DataSourceType value() default DataSourceType.MASTER;
}
