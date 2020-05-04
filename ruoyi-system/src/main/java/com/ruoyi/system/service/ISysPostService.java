package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysPost;

import java.util.List;

/**
 * 岗位信息 服务层
 *
 * @author ruoyi
 */
public interface ISysPostService extends IService<SysPost> {
  /**
   * 分页列出岗位
   *
   * @param page 分页对象
   * @param post 岗位
   * @return
   */
  IPage<SysPost> page(Page<SysPost> page, SysPost post);

  /**
   * 查询岗位信息集合
   *
   * @param post 岗位信息
   * @return 岗位信息集合
   */
  List<SysPost> list(SysPost post);

  /**
   * 根据用户ID查询岗位
   *
   * @param userId 用户ID
   * @return 岗位列表
   */
  List<SysPost> listByUser(Long userId);

  /**
   * 批量删除岗位信息
   *
   * @param ids 需要删除的数据ID
   * @return 结果
   * @throws Exception 异常
   */
  boolean removeByIds(String ids) throws Exception;

  /**
   * 校验岗位名称
   *
   * @param post 岗位信息
   * @return 结果
   */
  boolean checkNameUnique(SysPost post);

  /**
   * 校验岗位编码
   *
   * @param post 岗位信息
   * @return 结果
   */
  boolean checkCodeUnique(SysPost post);
}
