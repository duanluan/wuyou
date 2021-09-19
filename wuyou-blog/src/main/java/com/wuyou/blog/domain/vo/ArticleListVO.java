package com.wuyou.blog.domain.vo;

import com.wuyou.blog.domain.Article;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleListVO extends Article {

  private String authorName;
}
