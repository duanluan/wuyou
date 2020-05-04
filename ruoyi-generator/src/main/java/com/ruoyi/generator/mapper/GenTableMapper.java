package com.ruoyi.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.generator.domain.GenTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务 数据层
 *
 * @author ruoyi
 */
public interface GenTableMapper extends BaseMapper<GenTable> {

  /**
   * 分页查询业务列表
   *
   * @param page 分页对象
   * @param genTable 业务信息
   * @return
   */
  List<GenTable> page(Page<GenTable> page, @Param("genTable") GenTable genTable);

  /**
   * 分页查询据库列表
   *
   * @param page 分页对象
   * @param genTable 数据库表集合
   * @return
   */
  List<GenTable> pageDbTable(Page<GenTable> page, @Param("genTable") GenTable genTable);

  /**
   * 查询据库列表
   *
   * @param tableNames 表名称组
   * @return 数据库表集合
   */
  List<GenTable> listByNames(String[] tableNames);

  /**
   * 查询表ID业务信息
   *
   * @param id 业务ID
   * @return 业务信息
   */
  GenTable getById(Long id);

  /**
   * 查询表名称业务信息
   *
   * @param tableName 表名称
   * @return 业务信息
   */
  GenTable getByName(String tableName);
}