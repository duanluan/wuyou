package com.ruoyi.common.constant;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class Constants {
  /**
   * 通用成功标识
   */
  public static final int SUCCESS = 0;
  /**
   * 通用失败标识
   */
  public static final int FAIL = 1;
  /**
   * 登录成功
   */
  public static final String LOGIN_SUCCESS = "Success";
  /**
   * 注销
   */
  public static final String LOGOUT = "Logout";
  /**
   * 注册
   */
  public static final String REGISTER = "Register";
  /**
   * 登录失败
   */
  public static final String LOGIN_FAIL = "Error";
  /**
   * 当前记录起始索引
   */
  public static final String PAGE_NUM = "pageNum";
  /**
   * 每页显示记录数
   */
  public static final String PAGE_SIZE = "pageSize";
  /**
   * 排序列
   */
  public static final String ORDER_BY_COLUMN = "orderByColumn";
  /**
   * 排序的方向 "desc" 或者 "asc".
   */
  public static final String IS_ASC = "isAsc";
  /**
   * 资源映射路径 前缀
   */
  public static final String RESOURCE_PREFIX = "/profile";
  /**
   * 数据权限名称
   */
  public static final String DATA_SCOPE = "dataScope";
  /**
   * 数据权限 SQL
   */
  public static final String DATA_SCOPE_SQL = "${params.dataScope}";
}
