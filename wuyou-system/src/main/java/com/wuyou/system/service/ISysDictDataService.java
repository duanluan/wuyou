package com.wuyou.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyou.system.domain.SysDictData;

import java.util.List;

/**
 * 字典 业务层
 *
 * @author wuyou
 */
public interface ISysDictDataService extends IService<SysDictData> {
  /**
   * 分页列出字典数据
   *
   * @param page     分页对象
   * @param dictData 字典数据
   * @return
   */
  IPage<SysDictData> page(Page<SysDictData> page, SysDictData dictData);

  /**
   * 根据条件分页查询字典数据
   *
   * @param dictData 字典数据信息
   * @return 字典数据集合信息
   */
  List<SysDictData> list(SysDictData dictData);

  /**
   * 根据字典类型查询字典数据
   *
   * @param dictType 字典类型
   * @return 字典数据集合信息
   */
  List<SysDictData> listByType(String dictType);

  /**
   * 根据字典类型和字典键值查询字典数据信息
   *
   * @param dictType  字典类型
   * @param dictValue 字典键值
   * @return 字典标签
   */
  String getLabel(String dictType, String dictValue);

  /**
   * 批量删除字典数据
   *
   * @param ids 需要删除的数据
   * @return 结果
   */
  boolean removeByIds(String ids);

  /**
   * 新增保存字典数据信息
   *
   * @param dictData 字典数据信息
   * @return 结果
   */
  @Override
  boolean save(SysDictData dictData);

  /**
   * 修改保存字典数据信息
   *
   * @param dictData 字典数据信息
   * @return 结果
   */
  int update(SysDictData dictData);
}
