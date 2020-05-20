package com.wuyou.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.system.domain.SysDictType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表 数据层
 *
 * @author wuyou
 */
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {
  /**
   * 分页列出字典类型
   *
   * @param page     分页对象
   * @param dictType 字典类型
   * @return
   */
  List<SysDictType> page(Page<SysDictType> page, @Param("dictType") SysDictType dictType);
}
