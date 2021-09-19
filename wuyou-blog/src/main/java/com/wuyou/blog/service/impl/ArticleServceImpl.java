package com.wuyou.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.blog.domain.Article;
import com.wuyou.blog.domain.vo.ArticleListVO;
import com.wuyou.blog.mapper.ArticleMapper;
import com.wuyou.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

  @Autowired
  private ArticleMapper articleMapper;

  @Override
  public IPage<ArticleListVO> page(Page<ArticleListVO> page, ArticleListVO query) {
    return articleMapper.page(page, query);
  }
}
