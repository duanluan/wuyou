package com.wuyou.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyou.blog.domain.Article;
import com.wuyou.blog.domain.vo.ArticleListVO;

public interface ArticleService extends IService<Article> {

  IPage<ArticleListVO> page(Page<ArticleListVO> page, ArticleListVO query);
}
