package com.wuyou.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.common.constant.CacheConstants;
import com.wuyou.common.core.text.Convert;
import com.wuyou.common.utils.CacheUtils;
import com.wuyou.system.domain.SysConfig;
import com.wuyou.system.mapper.SysConfigMapper;
import com.wuyou.system.service.ISysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * 参数配置 服务层实现
 *
 * @author wuyou
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

  @Autowired
  private SysConfigMapper configMapper;

  /**
   * 项目启动时，初始化参数到缓存
   */
  @PostConstruct
  public void init() {
    List<SysConfig> configsList = super.list();
    for (SysConfig config : configsList) {
      CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
    }
  }

  /**
   * 根据键名查询参数配置信息
   *
   * @param configKey 参数key
   * @return 参数键值
   */
  @Override
  public String getValueByKey(String configKey) {
    String configValue = Convert.toStr(CacheUtils.get(getCacheName(), getCacheKey(configKey)));
    if (StringUtils.isNotEmpty(configValue)) {
      return configValue;
    }
    SysConfig configQuerier = new SysConfig();
    configQuerier.setConfigKey(configKey);
    SysConfig retConfig = super.getOne(new QueryWrapper<>(configQuerier));
    if (retConfig != null) {
      CacheUtils.put(getCacheName(), getCacheKey(configKey), retConfig.getConfigValue());
      return retConfig.getConfigValue();
    }
    return StringUtils.EMPTY;
  }

  @Override
  public IPage<SysConfig> page(Page<SysConfig> page, SysConfig config) {
    return configMapper.page(page, config);
  }

  /**
   * 新增参数配置
   *
   * @param config 参数配置信息
   * @return 结果
   */
  @Override
  public boolean save(SysConfig config) {
    boolean result = super.save(config);
    if (result) {
      CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
    }
    return result;
  }

  /**
   * 修改参数配置
   *
   * @param config 参数配置信息
   * @return 结果
   */
  @Override
  public boolean update(SysConfig config) {
    boolean result = super.updateById(config);
    if (result) {
      CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());
    }
    return result;
  }

  /**
   * 批量删除参数配置对象
   *
   * @param ids 需要删除的数据ID
   * @return 结果
   */
  @Override
  public boolean removeByIds(String ids) {
    boolean result = super.removeByIds(Arrays.asList(StringUtils.split(ids, ",")));
    if (result) {
      CacheUtils.removeAll(getCacheName());
    }
    return result;
  }

  /**
   * 清空缓存数据
   */
  @Override
  public void clearCache() {
    CacheUtils.removeAll(getCacheName());
  }

  /**
   * 校验参数键名是否唯一
   *
   * @param config 参数配置信息
   * @return 结果
   */
  @Override
  public boolean checkKeyUnique(SysConfig config) {
    long configId = config.getConfigId() == null ? -1L : config.getConfigId();

    SysConfig sysConfigQuerier = new SysConfig();
    sysConfigQuerier.setConfigKey(config.getConfigKey());
    SysConfig info = super.getOne(new QueryWrapper<>(sysConfigQuerier));
    return info != null && info.getConfigId() != configId;
  }

  /**
   * 获取cache name
   *
   * @return 缓存名
   */
  private String getCacheName() {
    return CacheConstants.SYS_CONFIG_NAME;
  }

  /**
   * 设置cache key
   *
   * @param configKey 参数键
   * @return 缓存键key
   */
  private String getCacheKey(String configKey) {
    return CacheConstants.SYS_CONFIG_KEY + configKey;
  }
}
