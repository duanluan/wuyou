package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysConfig;

/**
 * 参数配置 服务层
 *
 * @author ruoyi
 */
public interface ISysConfigService extends IService<SysConfig> {
  /**
   * 根据键名查询参数配置信息
   *
   * @param configKey 参数键名
   * @return 参数键值
   */
  String getValueByKey(String configKey);

  /**
   * 分页列出配置
   *
   * @param page   分页对象
   * @param config 配置
   * @return
   */
  IPage<SysConfig> page(Page<SysConfig> page, SysConfig config);

  /**
   * 新增参数配置
   *
   * @param config 参数配置信息
   * @return 结果
   */
  @Override
  boolean save(SysConfig config);

  /**
   * 修改参数配置
   *
   * @param config 参数配置信息
   * @return 结果
   */
  boolean update(SysConfig config);

  /**
   * 批量删除参数配置信息
   *
   * @param ids 需要删除的数据ID
   * @return 结果
   */
  boolean removeByIds(String ids);

  /**
   * 清空缓存数据
   */
  void clearCache();

  /**
   * 校验参数键名是否唯一
   *
   * @param config 参数信息
   * @return 结果
   */
  boolean checkKeyUnique(SysConfig config);
}
