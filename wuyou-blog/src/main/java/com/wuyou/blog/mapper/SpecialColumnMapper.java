package com.wuyou.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.blog.domain.Article;
import com.wuyou.blog.domain.SpecialColumn;
import com.wuyou.blog.domain.vo.ArticleListVO;
import com.wuyou.blog.domain.vo.SpecialColumnListVO;
import org.apache.ibatis.annotations.Param;

public interface SpecialColumnMapper extends BaseMapper<SpecialColumn> {


  IPage<SpecialColumnListVO> page(Page<SpecialColumnListVO> page, SpecialColumnListVO query);
}
