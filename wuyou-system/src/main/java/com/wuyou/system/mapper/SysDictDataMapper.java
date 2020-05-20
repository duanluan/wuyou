package com.wuyou.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.system.domain.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表 数据层
 *
 * @author wuyou
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {
  /**
   * 分页列出字典数据
   *
   * @param page     分页对象
   * @param dictData 字典数据
   * @return
   */
  List<SysDictData> page(Page<SysDictData> page, @Param("dictData") SysDictData dictData);
}
