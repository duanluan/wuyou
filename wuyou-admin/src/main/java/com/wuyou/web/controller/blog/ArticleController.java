package com.wuyou.web.controller.blog;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.blog.domain.Article;
import com.wuyou.blog.domain.vo.ArticleListVO;
import com.wuyou.blog.service.ArticleService;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.system.domain.SysRole;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/blog/articles")
@Controller
public class ArticleController  extends BaseController {

  private static final String PREFIX = "blog/article";

  // 依赖注入、自动注入
  @Autowired
  private ArticleService articleService;

  /**
   * 管理页面
   *
   * @return 管理页面路径
   */
  @RequiresPermissions("blog:article:view")
  @GetMapping
  public String list() {
    return PREFIX + "/list";
  }

  /**
   * 列出
   *
   * @param page 分页对象
   * @param query 查询条件
   * @return 列表
   */
  @RequiresPermissions("blog:article:list")
  @ResponseBody
  @GetMapping("/list")
  public Result list(Page<ArticleListVO> page, ArticleListVO query) {
    return Result.success(articleService.page(page, query));
  }
}
