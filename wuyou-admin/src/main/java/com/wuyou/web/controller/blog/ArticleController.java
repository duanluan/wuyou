package com.wuyou.web.controller.blog;

import com.wuyou.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/blog/articles")
@Controller
public class ArticleController  extends BaseController {

  private static final String PREFIX = "blog/article";

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
}
