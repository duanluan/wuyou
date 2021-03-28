package com.wuyou.generator.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyou.common.constant.GenConstants;
import com.wuyou.common.exception.BusinessException;
import com.wuyou.generator.domain.GenTable;
import com.wuyou.generator.domain.GenTableColumn;
import com.wuyou.generator.mapper.GenTableColumnMapper;
import com.wuyou.generator.mapper.GenTableMapper;
import com.wuyou.generator.service.IGenTableColumnService;
import com.wuyou.generator.service.IGenTableService;
import com.wuyou.generator.util.GenUtils;
import com.wuyou.generator.util.VelocityInitializer;
import com.wuyou.generator.util.VelocityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 业务 服务层实现
 *
 * @author wuyou
 */
@Slf4j
@Service
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements IGenTableService {

  private static final String ENCODING = StandardCharsets.UTF_8.name();

  @Autowired
  private GenTableMapper genTableMapper;
  @Autowired
  private GenTableColumnMapper genTableColumnMapper;
  @Autowired
  private IGenTableColumnService genTableColumnService;

  /**
   * 查询业务信息
   *
   * @param id 业务ID
   * @return 业务信息
   */
  @Override
  public GenTable getById(Long id) {
    GenTable genTable = genTableMapper.getById(id);
    setTableFromOptions(genTable);
    return genTable;
  }

  @Override
  public IPage<GenTable> page(Page<GenTable> page, GenTable genTable) {
    return page.setRecords(genTableMapper.page(page, genTable));
  }

  /**
   * 查询业务列表
   *
   * @param genTable 业务信息
   * @return 业务集合
   */
  @Override
  public List<GenTable> list(GenTable genTable) {
    return genTableMapper.page(null, genTable);
  }

  @Override
  public IPage<GenTable> pageDbTable(Page<GenTable> page, GenTable genTable) {
    return page.setRecords(genTableMapper.pageDbTable(page, genTable));
  }

  /**
   * 查询据库列表
   *
   * @param genTable 业务信息
   * @return 数据库表集合
   */
  @Override
  public List<GenTable> listDbTable(GenTable genTable) {
    return genTableMapper.pageDbTable(null, genTable);
  }

  /**
   * 查询据库列表
   *
   * @param tableNames 表名称组
   * @return 数据库表集合
   */
  @Override
  public List<GenTable> listDbTableByNames(String[] tableNames) {
    return genTableMapper.listByNames(tableNames);
  }

  /**
   * 查询所有表信息
   *
   * @return 表信息集合
   */
  @Override
  public List<GenTable> listAll() {
    return genTableMapper.selectGenTableAll();
  }

  /**
   * 修改业务
   *
   * @param genTable 业务信息
   */
  @Transactional
  @Override
  public void updateGenTable(GenTable genTable) {
    String options = JSON.toJSONString(genTable.getParams());
    genTable.setOptions(options);
    if (super.updateById(genTable)) {
      genTableColumnService.updateBatchById(genTable.getColumns());
    }
  }

  /**
   * 删除业务对象
   *
   * @param ids 需要删除的数据ID
   */
  @Transactional
  @Override
  public void removeByIds(String ids) {
    super.removeByIds(Arrays.asList(StringUtils.split(ids, ",")));
    genTableColumnService.removeByIds(Arrays.asList(StringUtils.split(ids, ",")));
  }

  /**
   * 导入表结构
   *
   * @param tableList 导入表列表
   * @param operName  操作人员
   */
  @Transactional
  @Override
  public void importGenTable(List<GenTable> tableList, String operName) {
    try {
      for (GenTable table : tableList) {
        String tableName = table.getTableName();
        GenUtils.initTable(table, operName);
        if (super.save(table)) {
          // 保存列信息
          List<GenTableColumn> genTableColumns = genTableColumnMapper.listByTableName(tableName);
          for (GenTableColumn column : genTableColumns) {
            GenUtils.initColumnField(column, table);
            genTableColumnMapper.insert(column);
          }
        }
      }
    } catch (Exception e) {
      throw new BusinessException("导入失败：" + e.getMessage());
    }
  }

  /**
   * 预览代码
   *
   * @param tableId 表编号
   * @return 预览数据列表
   */
  @Override
  public Map<String, String> previewCode(Long tableId) {
    Map<String, String> dataMap = new LinkedHashMap<>();
    // 查询表信息
    GenTable table = genTableMapper.getById(tableId);
    // 设置主子表信息
    setSubTable(table);
    // 设置主键列信息
    setPkColumn(table);
    VelocityInitializer.initVelocity();

    VelocityContext context = VelocityUtils.prepareContext(table);

    // 获取模板列表
    List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
    for (String template : templates) {
      // 渲染模板
      StringWriter sw = new StringWriter();
      Template tpl = Velocity.getTemplate(template, ENCODING);
      tpl.merge(context, sw);
      dataMap.put(template, sw.toString());
    }
    return dataMap;
  }

  /**
   * 生成代码（下载方式）
   *
   * @param tableName 表名称
   * @return 数据
   */
  @Override
  public byte[] downloadCode(String tableName) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try (ZipOutputStream zip = new ZipOutputStream(outputStream)) {
      generatorCode(tableName, zip);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return outputStream.toByteArray();
  }

  /**
   * 生成代码（自定义路径）
   *
   * @param tableName 表名称
   */
  @Override
  public void generatorCode(String tableName) {
    // 查询表信息
    GenTable queryGenTable = new GenTable();
    queryGenTable.setTableName(tableName);
    GenTable table = this.getOne(new QueryWrapper<>(queryGenTable));
    // 设置主子表信息
    setSubTable(table);
    // 设置主键列信息
    setPkColumn(table);

    VelocityInitializer.initVelocity();

    VelocityContext context = VelocityUtils.prepareContext(table);

    // 获取模板列表
    List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
    for (String template : templates) {
      if (!StringUtils.contains(template, "sql.vm")) {
        // 渲染模板
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(template, StandardCharsets.UTF_8.name());
        tpl.merge(context, sw);
        try {
          String path = getGenPath(table, template);
          FileUtils.writeStringToFile(new File(path), sw.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
          throw new BusinessException("渲染模板失败，表名：" + table.getTableName());
        }
      }
    }
  }

  /**
   * 同步数据库
   *
   * @param tableName 表名称
   */
  @Override
  @Transactional
  public void synchDb(String tableName) {
    GenTable table = genTableMapper.getByName(tableName);
    List<GenTableColumn> tableColumns = table.getColumns();
    List<String> tableColumnNames = tableColumns.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());

    List<GenTableColumn> dbTableColumnList = genTableColumnMapper.listByTableName(tableName);
    if (CollectionUtils.sizeIsEmpty(dbTableColumnList)) {
      throw new BusinessException("同步数据失败，原表结构不存在");
    }
    List<String> dbTableColumnNameList = dbTableColumnList.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());

    dbTableColumnList.forEach(column -> {
      if (!tableColumnNames.contains(column.getColumnName())) {
        GenUtils.initColumnField(column, table);
        genTableColumnMapper.insert(column);
      }
    });

    List<Long> columnIdList = tableColumns.stream().filter(column -> !dbTableColumnNameList.contains(column.getColumnName())).map(GenTableColumn::getColumnId).collect(Collectors.toList());
    if (!CollectionUtils.sizeIsEmpty(columnIdList)) {
      genTableColumnMapper.deleteBatchIds(columnIdList);
    }
  }

  /**
   * 批量生成代码（下载方式）
   *
   * @param tableNames 表数组
   * @return 数据
   */
  @Override
  public byte[] downloadCode(String[] tableNames) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try (ZipOutputStream zip = new ZipOutputStream(outputStream)) {
      for (String tableName : tableNames) {
        generatorCode(tableName, zip);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return outputStream.toByteArray();
  }

  /**
   * 查询表信息并生成代码
   */
  private void generatorCode(String tableName, ZipOutputStream zip) {
    // 查询表信息
    GenTable table = genTableMapper.getByName(tableName);
    // 设置主子表信息
    setSubTable(table);
    // 设置主键列信息
    setPkColumn(table);

    VelocityInitializer.initVelocity();

    VelocityContext context = VelocityUtils.prepareContext(table);

    // 获取模板列表
    List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
    for (String template : templates) {
      // 渲染模板
      StringWriter sw = new StringWriter();
      Template tpl = Velocity.getTemplate(template, ENCODING);
      tpl.merge(context, sw);
      try {
        // 添加到zip
        zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
        IOUtils.write(sw.toString(), zip, ENCODING);
        sw.close();
        zip.flush();
        zip.closeEntry();
      } catch (IOException e) {
        log.error("渲染模板失败，表名：" + table.getTableName(), e);
      }
    }
  }

  /**
   * 修改保存参数校验
   *
   * @param genTable 业务信息
   */
  @Override
  public void validateEdit(GenTable genTable) {
    if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
      String options = JSON.toJSONString(genTable.getParams());
      JSONObject paramsObj = JSONObject.parseObject(options);
      if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
        throw new BusinessException("树编码字段不能为空");
      } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
        throw new BusinessException("树父编码字段不能为空");
      } else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
        throw new BusinessException("树名称字段不能为空");
      } else if (GenConstants.TPL_SUB.equals(genTable.getTplCategory())) {
        if (StringUtils.isEmpty(genTable.getSubTableName())) {
          throw new BusinessException("关联子表的表名不能为空");
        } else if (StringUtils.isEmpty(genTable.getSubTableFkName())) {
          throw new BusinessException("子表关联的外键名不能为空");
        }
      }
    }
  }

  /**
   * 设置主键列信息
   *
   * @param table 业务表信息
   */
  public void setPkColumn(GenTable table) {
    for (GenTableColumn column : table.getColumns()) {
      if (column.isPk()) {
        table.setPkColumn(column);
        break;
      }
    }
    if (table.getPkColumn() == null) {
      table.setPkColumn(table.getColumns().get(0));
    }
    if (GenConstants.TPL_SUB.equals(table.getTplCategory())) {
      for (GenTableColumn column : table.getSubTable().getColumns()) {
        if (column.isPk()) {
          table.getSubTable().setPkColumn(column);
          break;
        }
      }
      if (table.getSubTable().getPkColumn() == null) {
        table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
      }
    }
  }

  /**
   * 设置主子表信息
   *
   * @param table 业务表信息
   */
  public void setSubTable(GenTable table) {
    String subTableName = table.getSubTableName();
    if (StringUtils.isNotEmpty(subTableName)) {
      table.setSubTable(genTableMapper.getByName(subTableName));
    }
  }

  /**
   * 设置代码生成其他选项值
   *
   * @param genTable 设置后的生成对象
   */
  public void setTableFromOptions(GenTable genTable) {
    JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
    if (paramsObj != null) {
      String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
      String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
      String treeName = paramsObj.getString(GenConstants.TREE_NAME);
      String parentMenuId = paramsObj.getString(GenConstants.PARENT_MENU_ID);
      String parentMenuName = paramsObj.getString(GenConstants.PARENT_MENU_NAME);
      genTable.setTreeCode(treeCode);
      genTable.setTreeParentCode(treeParentCode);
      genTable.setTreeName(treeName);
      genTable.setParentMenuId(parentMenuId);
      genTable.setParentMenuName(parentMenuName);
    }
  }

  /**
   * 获取代码生成地址
   *
   * @param table    业务表信息
   * @param template 模板文件路径
   * @return 生成地址
   */
  public static String getGenPath(GenTable table, String template) {
    String genPath = table.getGenPath();
    if (StringUtils.equals(genPath, "/")) {
      return System.getProperty("user.dir") + File.separator + "src" + File.separator + VelocityUtils.getFileName(template, table);
    }
    return genPath + File.separator + VelocityUtils.getFileName(template, table);
  }
}