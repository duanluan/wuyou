package com.wuyou.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.system.domain.SysDictData;
import com.wuyou.system.mapper.SysDictDataMapper;
import com.wuyou.system.service.ISysDictDataService;
import com.wuyou.system.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author wuyou
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

  @Autowired
  private SysDictDataMapper dictDataMapper;

  @Override
  public IPage<SysDictData> page(Page<SysDictData> page, SysDictData dictData) {
    return page.setRecords(dictDataMapper.page(page, dictData));
  }

  /**
   * 根据条件分页查询字典数据
   *
   * @param dictData 字典数据信息
   * @return 字典数据集合信息
   */
  @Override
  public List<SysDictData> list(SysDictData dictData) {
    return dictDataMapper.page(null, dictData);
  }

  /**
   * 根据字典类型查询字典数据
   *
   * @param dictType 字典类型
   * @return 字典数据集合信息
   */
  @Override
  public List<SysDictData> listByType(String dictType) {
    List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
    if (dictDatas != null) {
      return dictDatas;
    }

    SysDictData sysDictDataQuerier = new SysDictData();
    sysDictDataQuerier.setStatus(0);
    sysDictDataQuerier.setDictType(dictType);
    dictDatas = super.list(new QueryWrapper<>(sysDictDataQuerier).orderByAsc("dict_sort"));
    if (dictDatas != null) {
      DictUtils.setDictCache(dictType, dictDatas);
      return dictDatas;
    }
    return null;
  }

  /**
   * 根据字典类型和字典键值查询字典数据信息
   *
   * @param dictType  字典类型
   * @param dictValue 字典键值
   * @return 字典标签
   */
  @Override
  public String getLabel(String dictType, String dictValue) {
    String result = null;

    SysDictData sysDictDataQuerier = new SysDictData();
    sysDictDataQuerier.setDictType(dictType);
    sysDictDataQuerier.setDictValue(dictValue);
    SysDictData dictData = super.getOne(new QueryWrapper<>(sysDictDataQuerier));
    if (dictData != null) {
      result = dictData.getDictLabel();
    }
    return result;
  }

  /**
   * 批量删除字典数据
   *
   * @param ids 需要删除的数据
   * @return 结果
   */
  @Override
  public boolean removeByIds(String ids) {
    boolean result = super.removeByIds(Arrays.asList(StringUtils.split(ids, ",")));
    if (result) {
      DictUtils.clearDictCache();
    }
    return result;
  }

  /**
   * 新增保存字典数据信息
   *
   * @param dictData 字典数据信息
   * @return 结果
   */
  @Override
  public boolean save(SysDictData dictData) {
    boolean result = super.save(dictData);
    if (result) {
      DictUtils.clearDictCache();
    }
    return result;
  }

  /**
   * 修改保存字典数据信息
   *
   * @param dictData 字典数据信息
   * @return 结果
   */
  @Override
  public int update(SysDictData dictData) {
    int row = dictDataMapper.updateById(dictData);
    if (row > 0) {
      DictUtils.clearDictCache();
    }
    return row;
  }
}
