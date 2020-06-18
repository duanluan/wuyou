package com.wuyou.web.controller.demo.domain;

import lombok.Data;

import java.util.List;

/**
 * 客户测试信息
 *
 * @author wuyou
 */
@Data
public class CustomerModel {
  /**
   * 客户姓名
   */
  private String name;

  /**
   * 客户手机
   */
  private String phonenumber;

  /**
   * 客户性别
   */
  private String sex;

  /**
   * 客户生日
   */
  private String birthday;

  /**
   * 客户描述
   */
  private String remark;

  /**
   * 商品信息
   */
  private List<GoodsModel> goods;
}
