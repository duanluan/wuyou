package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.system.domain.SysDictData;
import com.ruoyi.system.domain.SysDictType;
import com.ruoyi.system.enums.SysDictTypeStatus;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysDictTypeMapper;
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.system.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

  @Autowired
  private SysDictTypeMapper dictTypeMapper;
  @Autowired
  private SysDictDataMapper dictDataMapper;

  /**
   * 项目启动时，初始化字典到缓存
   */
  @PostConstruct
  public void init() {
    List<SysDictType> dictTypeList = super.list();
    for (SysDictType dictType : dictTypeList) {

      SysDictData dictDataQuerier = new SysDictData();
      dictDataQuerier.setStatus(0);
      dictDataQuerier.setDictType(dictType.getDictType());
      List<SysDictData> dictDatas = dictDataMapper.selectList(new QueryWrapper<>(dictDataQuerier).orderByAsc("dict_sort"));
      DictUtils.setDictCache(dictType.getDictType(), dictDatas);
    }
  }

  @Override
  public IPage<SysDictType> page(Page<SysDictType> page, SysDictType dictType) {
    return page.setRecords(dictTypeMapper.page(page, dictType));
  }

  /**
   * 根据条件分页查询字典类型
   *
   * @param dictType 字典类型信息
   * @return 字典类型集合信息
   */
  @Override
  public List<SysDictType> list(SysDictType dictType) {
    return dictTypeMapper.page(null, dictType);
  }

  /**
   * 批量删除字典类型
   *
   * @param ids 需要删除的数据
   * @return 结果
   */
  @Override
  public boolean removeByIds(String ids) {
    String[] dictIds = StringUtils.split(ids, ",");
    for (String dictId : dictIds) {
      SysDictType dictType = getById(dictId);
      SysDictType sysDictTypeQuerier = new SysDictType();
      sysDictTypeQuerier.setDictType(dictType.getDictType());
      if (super.count(new QueryWrapper<>(sysDictTypeQuerier)) > 0) {
        throw new BusinessException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
      }
    }
    boolean result = super.removeByIds(Arrays.asList(dictIds));
    if (result) {
      DictUtils.clearDictCache();
    }
    return result;
  }

  /**
   * 清空缓存数据
   */
  @Override
  public void clearCache() {
    DictUtils.clearDictCache();
  }

  /**
   * 新增保存字典类型信息
   *
   * @param dictType 字典类型信息
   * @return 结果
   */
  @Override
  public boolean save(SysDictType dictType) {
    boolean result = super.save(dictType);
    if (result) {
      DictUtils.clearDictCache();
    }
    return result;
  }

  /**
   * 修改保存字典类型信息
   *
   * @param dictType 字典类型信息
   * @return 结果
   */
  @Transactional
  @Override
  public boolean update(SysDictType dictType) {
    boolean result = super.updateById(dictType);
    if (result) {
      DictUtils.clearDictCache();
    }
    return result;
  }

  /**
   * 校验字典类型称是否唯一
   *
   * @param dict 字典类型
   * @return 结果
   */
  @Override
  public boolean checkTypeUnique(SysDictType dict) {
    long dictId = dict.getDictId() == null ? -1L : dict.getDictId();

    SysDictType sysDictTypeQuerier = new SysDictType();
    sysDictTypeQuerier.setDictType(dict.getDictType());
    SysDictType dictType = super.getOne(new QueryWrapper<>(sysDictTypeQuerier));
    return dictType != null && dictType.getDictId() != dictId;
  }

  /**
   * 查询字典类型树
   *
   * @param dictType 字典类型
   * @return 所有字典类型
   */
  @Override
  public List<Ztree> listTree(SysDictType dictType) {
    List<Ztree> ztrees = new ArrayList<>();
    List<SysDictType> dictList = dictTypeMapper.page(null, dictType);
    for (SysDictType dict : dictList) {
      if (SysDictTypeStatus.NORMAL.getValue().equals(dict.getStatus())) {
        Ztree ztree = new Ztree();
        ztree.setId(dict.getDictId());
        ztree.setName(transName(dict));
        ztree.setTitle(dict.getDictType());
        ztrees.add(ztree);
      }
    }
    return ztrees;
  }

  public String transName(SysDictType dictType) {
    return "(" + dictType.getDictName() + ")" + "&nbsp;&nbsp;&nbsp;" + dictType.getDictType();
  }
}
