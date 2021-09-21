package com.wuyou.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyou.blog.domain.Article;
import com.wuyou.blog.domain.SpecialColumn;
import com.wuyou.blog.domain.vo.ArticleListVO;
import com.wuyou.blog.domain.vo.SpecialColumnListVO;

public interface SpecialColumnService extends IService<SpecialColumn> {

  IPage<SpecialColumnListVO> page(Page<SpecialColumnListVO> page, SpecialColumnListVO query);
}
