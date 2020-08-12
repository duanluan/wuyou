package com.wuyou.web.controller.demo.domain;

import lombok.Data;

import java.util.Date;

/**
 * 商品测试信息
 *
 * @author wuyou
 */
@Data
public class GoodsModel {
  /**
   * 商品名称
   */
  private String name;
  /**
   * 商品日期
   */
  private Date date;
  /**
   * 商品重量
   */
  private Integer weight;
  /**
   * 商品价格
   */
  private Double price;
  /**
   * 商品种类
   */
  private String type;
}
