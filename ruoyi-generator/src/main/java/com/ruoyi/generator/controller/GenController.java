package com.ruoyi.generator.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.security.PermissionUtils;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.domain.GenTableColumn;
import com.ruoyi.generator.service.IGenTableColumnService;
import com.ruoyi.generator.service.IGenTableService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/tool/gen")
public class GenController extends BaseController {

  private static final String PREFIX = "tool/gen";

  @Autowired
  private IGenTableService genTableService;
  @Autowired
  private IGenTableColumnService genTableColumnService;

  @RequiresPermissions("tool:gen:view")
  @GetMapping
  public String gen() {
    return PREFIX + "/gen";
  }

  /**
   * 查询代码生成列表
   */
  @RequiresPermissions("tool:gen:list")
  @PostMapping("/list")
  @ResponseBody
  public Result genList(Page<GenTable> page, GenTable genTable) {
    return Result.success(genTableService.page(page, genTable));
  }

  /**
   * 查询数据库列表
   */
  @RequiresPermissions("tool:gen:list")
  @PostMapping("/db/list")
  @ResponseBody
  public Result dataList(Page<GenTable> page, GenTable genTable) {
    return Result.success(genTableService.pageDbTable(page, genTable));
  }

  /**
   * 查询数据表字段列表
   */
  @RequiresPermissions("tool:gen:list")
  @PostMapping("/column/list")
  @ResponseBody
  public TableDataInfo columnList(GenTableColumn genTableColumn) {
    TableDataInfo dataInfo = new TableDataInfo();
    GenTableColumn genTableColumnQuerier = new GenTableColumn();
    genTableColumnQuerier.setTableId(genTableColumn.getTableId());
    List<GenTableColumn> list = genTableColumnService.list(new QueryWrapper<>(genTableColumnQuerier));
    dataInfo.setRows(list);
    dataInfo.setTotal(list.size());
    return dataInfo;
  }

  /**
   * 导入表结构
   */
  @RequiresPermissions("tool:gen:list")
  @GetMapping("/importTable")
  public String importTable() {
    return PREFIX + "/importTable";
  }

  /**
   * 导入表结构（保存）
   */
  @RequiresPermissions("tool:gen:list")
  @Log(title = "代码生成", businessType = BusinessType.IMPORT)
  @PostMapping("/importTable")
  @ResponseBody
  public Result importTableSave(String tables) {
    String[] tableNames = Convert.toStrArray(tables);
    // 查询表信息
    List<GenTable> tableList = genTableService.listDbTableByNames(tableNames);
    String operName = (String) PermissionUtils.getPrincipalProperty("loginName");
    genTableService.importGenTable(tableList, operName);
    return Result.success();
  }

  /**
   * 修改代码生成业务
   */
  @GetMapping("/edit/{tableId}")
  public String edit(@PathVariable("tableId") Long tableId, ModelMap mmap) {
    GenTable table = genTableService.getById(tableId);
    mmap.put("table", table);
    return PREFIX + "/edit";
  }

  /**
   * 修改保存代码生成业务
   */
  @RequiresPermissions("tool:gen:edit")
  @Log(title = "代码生成", businessType = BusinessType.UPDATE)
  @PostMapping("/edit")
  @ResponseBody
  public Result editSave(@Validated GenTable genTable) {
    genTableService.validateEdit(genTable);
    genTableService.updateGenTable(genTable);
    return Result.success();
  }

  @RequiresPermissions("tool:gen:remove")
  @Log(title = "代码生成", businessType = BusinessType.DELETE)
  @PostMapping("/remove")
  @ResponseBody
  public Result remove(String ids) {
    genTableService.removeByIds(ids);
    return Result.success();
  }

  /**
   * 预览代码
   */
  @RequiresPermissions("tool:gen:preview")
  @GetMapping("/preview/{tableId}")
  @ResponseBody
  public Result preview(@PathVariable("tableId") Long tableId) {
    Map<String, String> dataMap = genTableService.previewCode(tableId);
    return Result.success(dataMap);
  }

  /**
   * 生成代码
   */
  @RequiresPermissions("tool:gen:code")
  @Log(title = "代码生成", businessType = BusinessType.GENCODE)
  @GetMapping("/genCode/{tableName}")
  public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
    byte[] data = genTableService.generatorCode(tableName);
    genCode(response, data);
  }

  /**
   * 批量生成代码
   */
  @RequiresPermissions("tool:gen:code")
  @Log(title = "代码生成", businessType = BusinessType.GENCODE)
  @GetMapping("/batchGenCode")
  @ResponseBody
  public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
    String[] tableNames = Convert.toStrArray(tables);
    byte[] data = genTableService.generatorCode(tableNames);
    genCode(response, data);
  }

  /**
   * 生成zip文件
   */
  private void genCode(HttpServletResponse response, byte[] data) throws IOException {
    response.reset();
    response.setHeader("Content-Disposition", "attachment; filename=\"ruoyi.zip\"");
    response.addHeader("Content-Length", "" + data.length);
    response.setContentType("application/octet-stream; charset=UTF-8");
    IOUtils.write(data, response.getOutputStream());
  }
}