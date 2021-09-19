package com.wuyou.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.blog.domain.Article;
import com.wuyou.blog.domain.vo.ArticleListVO;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper extends BaseMapper<Article> {

  IPage<ArticleListVO> page(Page<ArticleListVO> page, @Param("query") ArticleListVO query);
}
