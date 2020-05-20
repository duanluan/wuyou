package com.wuyou.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyou.common.core.domain.Ztree;
import com.wuyou.system.domain.SysDictType;

import java.util.List;

/**
 * 字典 业务层
 *
 * @author wuyou
 */
public interface ISysDictTypeService extends IService<SysDictType> {
  /**
   * 分页列出字典
   *
   * @param page     分页对象
   * @param dictType 字典
   * @return
   */
  IPage<SysDictType> page(Page<SysDictType> page, SysDictType dictType);

  /**
   * 根据条件分页查询字典类型
   *
   * @param dictType 字典类型信息
   * @return 字典类型集合信息
   */
  List<SysDictType> list(SysDictType dictType);

  /**
   * 批量删除字典类型
   *
   * @param ids 需要删除的数据
   * @return 结果
   * @throws Exception 异常
   */
  boolean removeByIds(String ids);

  /**
   * 清空缓存数据
   */
  void clearCache();

  /**
   * 新增保存字典类型信息
   *
   * @param dictType 字典类型信息
   * @return 结果
   */
  @Override
  boolean save(SysDictType dictType);

  /**
   * 修改保存字典类型信息
   *
   * @param dictType 字典类型信息
   * @return 结果
   */
  boolean update(SysDictType dictType);

  /**
   * 校验字典类型称是否唯一
   *
   * @param dictType 字典类型
   * @return 结果
   */
  boolean checkTypeUnique(SysDictType dictType);

  /**
   * 查询字典类型树
   *
   * @param dictType 字典类型
   * @return 所有字典类型
   */
  List<Ztree> listTree(SysDictType dictType);
}
