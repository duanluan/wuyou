package com.wuyou.system.utils;

import com.wuyou.common.constant.CacheConstants;
import com.wuyou.common.utils.CacheUtils;
import com.wuyou.common.utils.StringUtils;
import com.wuyou.system.domain.SysDictData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 字典工具类
 *
 * @author wuyou
 */
@Component
public class DictUtils {
  /**
   * 设置字典缓存
   *
   * @param key       参数键
   * @param dictDatas 字典数据列表
   */
  public static void setDictCache(String key, List<SysDictData> dictDatas) {
    CacheUtils.put(getCacheName(), getCacheKey(key), dictDatas);
  }

  /**
   * 获取字典缓存
   *
   * @param key 参数键
   * @return dictDatas 字典数据列表
   */
  public static List<SysDictData> getDictCache(String key) {
    Object cacheObj = CacheUtils.get(getCacheName(), getCacheKey(key));
    if (cacheObj != null) {
      return StringUtils.cast(cacheObj);
    }
    return null;
  }

  /**
   * 根据字典类型和字典值获取字典标签
   *
   * @param dictType  字典类型
   * @param dictValue 字典值
   * @return 字典标签
   */
  public static String getDictLabel(String dictType, String dictValue) {
    if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotEmpty(dictValue)) {
      List<SysDictData> datas = getDictCache(dictType);
      if (!CollectionUtils.sizeIsEmpty(datas)) {
        for (SysDictData dict : datas) {
          if (dictValue.equals(dict.getDictValue())) {
            return dict.getDictLabel();
          }
        }
      }
    }
    return dictValue;
  }

  /**
   * 根据字典类型和字典标签获取字典值
   *
   * @param dictType  字典类型
   * @param dictLabel 字典标签
   * @return 字典值
   */
  public static String getDictValue(String dictType, String dictLabel) {
    if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotEmpty(dictLabel)) {
      List<SysDictData> datas = getDictCache(dictType);
      if (!CollectionUtils.sizeIsEmpty(datas)) {
        for (SysDictData dict : datas) {
          if (dictLabel.equals(dict.getDictLabel())) {
            return dict.getDictValue();
          }
        }
      }
    }
    return dictLabel;
  }

  /**
   * 清空字典缓存
   */
  public static void clearDictCache() {
    CacheUtils.removeAll(getCacheName());
  }

  /**
   * 获取cache name
   *
   * @return 缓存名
   */
  public static String getCacheName() {
    return CacheConstants.SYS_DICT_NAME;
  }

  /**
   * 设置cache key
   *
   * @param configKey 参数键
   * @return 缓存键key
   */
  public static String getCacheKey(String configKey) {
    return CacheConstants.SYS_DICT_KEY + configKey;
  }
}
