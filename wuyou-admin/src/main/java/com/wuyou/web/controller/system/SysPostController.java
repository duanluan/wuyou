package com.wuyou.web.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyou.common.annotation.Log;
import com.wuyou.common.core.controller.BaseController;
import com.wuyou.common.core.domain.Result;
import com.wuyou.common.enums.BusinessType;
import com.wuyou.common.utils.NumberUtils;
import com.wuyou.common.utils.StringUtils;
import com.wuyou.common.utils.poi.ExcelUtil;
import com.wuyou.framework.util.ShiroUtils;
import com.wuyou.system.domain.SysPost;
import com.wuyou.system.service.ISysPostService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wuyou.common.core.domain.Result.custom;
import static com.wuyou.common.core.domain.Result.error;

/**
 * 岗位信息操作处理
 *
 * @author wuyou
 */
@RequestMapping("/system/posts")
@Controller
public class SysPostController extends BaseController {

  private static final String PREFIX = "system/post";
  private static final String LOG_TITLE = "岗位管理";

  @Autowired
  private ISysPostService postService;

  /**
   * 管理页面
   *
   * @return 管理页面路径
   */
  @RequiresPermissions("system:post:view")
  @GetMapping
  public String list() {
    return PREFIX + "/list";
  }

  /**
   * 列出
   *
   * @param page 分页对象
   * @param post 查询条件
   * @return 岗位列表
   */
  @RequiresPermissions("system:post:list")
  @ResponseBody
  @GetMapping("/list")
  public Result list(Page<SysPost> page, SysPost post) {
    return Result.success(postService.page(page, post));
  }

  /**
   * 导出 Excel
   *
   * @param post 查询条件
   * @return Excel 文件
   */
  @Log(title = LOG_TITLE, businessType = BusinessType.EXPORT)
  @RequiresPermissions("system:post:export")
  @ResponseBody
  @PostMapping("/export")
  public Result export(SysPost post) {
    List<SysPost> list = postService.list(post);
    ExcelUtil<SysPost> util = new ExcelUtil<>(SysPost.class);
    return util.exportExcel(list, "岗位数据");
  }

  /**
   * 删除
   *
   * @param ids 岗位 ID 集合
   * @return 是否成功
   */
  @Log(title = LOG_TITLE, businessType = BusinessType.DELETE)
  @RequiresPermissions("system:post:remove")
  @ResponseBody
  @DeleteMapping
  public Result remove(String ids) {
    try {
      return custom(postService.removeByIds(ids));
    } catch (Exception e) {
      return error(e.getMessage());
    }
  }

  /**
   * 编辑页面
   *
   * @param postId 岗位 ID
   * @param mmap
   * @return 编辑页面路径 b
   */
  @GetMapping("/{postId}")
  public String edit(@PathVariable("postId") Long postId, ModelMap mmap) {
    if (NumberUtils.greaterThanZero(postId)) {
      mmap.put("post", postService.getById(postId));
    }
    return PREFIX + "/edit";
  }

  /**
   * 保存
   *
   * @param post 岗位
   * @return 是否成功
   */
  @Log(title = LOG_TITLE, businessType = BusinessType.INSERT)
  @RequiresPermissions("system:post:add")
  @ResponseBody
  @PostMapping
  public Result addSave(@Validated SysPost post) {
    if (postService.checkNameUnique(post)) {
      return error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
    } else if (postService.checkCodeUnique(post)) {
      return error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
    }
    post.setCreateBy(ShiroUtils.getLoginName());
    return custom(postService.save(post));
  }

  /**
   * 修改
   *
   * @param post 岗位
   * @return 是否成功
   */
  @Log(title = LOG_TITLE, businessType = BusinessType.UPDATE)
  @RequiresPermissions("system:post:edit")
  @ResponseBody
  @PutMapping("/{postId}")
  public Result editSave(@PathVariable("postId") Long postId, @Validated SysPost post) {
    if (postService.checkNameUnique(post)) {
      return error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
    } else if (postService.checkCodeUnique(post)) {
      return error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
    }
    post.setUpdateBy(ShiroUtils.getLoginName());
    return custom(postService.updateById(post));
  }

  /**
   * 校验是否唯一
   *
   * @param postId 岗位 ID
   * @param post   岗位数据：名称、编码
   * @return
   */
  @ResponseBody
  @GetMapping("/{postId}/checkUnique")
  public boolean checkUnique(@PathVariable("postId") Long postId, @RequestParam SysPost post) {
    if (post == null || NumberUtils.lessThanZero(postId) || (StringUtils.isBlank(post.getPostName()) && StringUtils.isBlank(post.getPostCode()))) {
      return false;
    }
    if (StringUtils.isNotBlank(post.getPostName())) {
      return postService.checkNameUnique(post);
    }
    if (StringUtils.isNotBlank(post.getPostCode())) {
      return postService.checkCodeUnique(post);
    }
    return false;
  }
}
