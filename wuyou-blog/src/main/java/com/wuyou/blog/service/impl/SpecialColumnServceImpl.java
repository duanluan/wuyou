package com.wuyou.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.blog.domain.Article;
import com.wuyou.blog.domain.SpecialColumn;
import com.wuyou.blog.domain.vo.ArticleListVO;
import com.wuyou.blog.domain.vo.SpecialColumnListVO;
import com.wuyou.blog.mapper.ArticleMapper;
import com.wuyou.blog.mapper.SpecialColumnMapper;
import com.wuyou.blog.service.ArticleService;
import com.wuyou.blog.service.SpecialColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialColumnServceImpl extends ServiceImpl<SpecialColumnMapper, SpecialColumn> implements SpecialColumnService {

  @Autowired
  private SpecialColumnMapper specialColumnMapper;

  @Override
  public IPage<SpecialColumnListVO> page(Page<SpecialColumnListVO> page, SpecialColumnListVO query) {
    return specialColumnMapper.page(page,query);
  }
}
