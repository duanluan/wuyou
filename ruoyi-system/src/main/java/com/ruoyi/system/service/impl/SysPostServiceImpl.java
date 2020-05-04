package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.domain.SysUserPost;
import com.ruoyi.system.mapper.SysPostMapper;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysUserPostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 岗位信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

  @Autowired
  private SysPostMapper postMapper;
  @Autowired
  private ISysUserPostService userPostService;

  @Override
  public IPage<SysPost> page(Page<SysPost> page, SysPost post) {
    return page.setRecords(postMapper.page(page, post));
  }

  /**
   * 查询岗位信息集合
   *
   * @param post 岗位信息
   * @return 岗位信息集合
   */
  @Override
  public List<SysPost> list(SysPost post) {
    return postMapper.page(null, post);
  }

  /**
   * 根据用户ID查询岗位
   *
   * @param userId 用户ID
   * @return 岗位列表
   */
  @Override
  public List<SysPost> listByUser(Long userId) {
    List<SysPost> userPosts = postMapper.listByUser(userId);
    List<SysPost> posts = super.list();
    for (SysPost post : posts) {
      for (SysPost userRole : userPosts) {
        if (post.getPostId().longValue() == userRole.getPostId().longValue()) {
          post.setFlag(true);
          break;
        }
      }
    }
    return posts;
  }

  /**
   * 批量删除岗位信息
   *
   * @param ids 需要删除的数据ID
   * @return
   * @throws Exception
   */
  @Override
  public boolean removeByIds(String ids) throws BusinessException {
    String[] postIds = StringUtils.split(ids, ",");
    for (String postId : postIds) {
      SysPost post = getById(postId);
      SysUserPost sysUserPostQuerier = new SysUserPost();
      sysUserPostQuerier.setPostId(Long.valueOf(postId));
      if (userPostService.count(new QueryWrapper<>(sysUserPostQuerier)) > 0) {
        throw new BusinessException(String.format("%1$s已分配,不能删除", post.getPostName()));
      }
    }
    return super.removeByIds(Arrays.asList(StringUtils.split(ids, ",")));
  }

  /**
   * 校验岗位名称是否唯一
   *
   * @param post 岗位信息
   * @return 结果
   */
  @Override
  public boolean checkNameUnique(SysPost post) {
    long postId = post.getPostId() == null ? -1L : post.getPostId();
    SysPost sysPostQuerier = new SysPost();
    sysPostQuerier.setPostName(post.getPostName());
    SysPost info = super.getOne(new QueryWrapper<>(sysPostQuerier));
    return info != null && info.getPostId() != postId;
  }

  /**
   * 校验岗位编码是否唯一
   *
   * @param post 岗位信息
   * @return 结果
   */
  @Override
  public boolean checkCodeUnique(SysPost post) {
    long postId = post.getPostId() == null ? -1L : post.getPostId();

    SysPost sysPostQuerier = new SysPost();
    sysPostQuerier.setPostCode(post.getPostCode());
    SysPost info = super.getOne(new QueryWrapper<>(sysPostQuerier));
    return info != null && info.getPostId() != postId;
  }
}
