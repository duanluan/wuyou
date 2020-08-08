/*
 Navicat Premium Data Transfer

 Source Server         : Localhost_MySQL
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : wuyou

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 03/08/2020 16:20:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` tinyint(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '生成代码方式（0: zip 压缩包, 1: 自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他生成选项',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成业务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` tinyint(1) NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` tinyint(1) NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` tinyint(1) NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` tinyint(1) NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` tinyint(1) NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` tinyint(1) NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` tinyint(1) NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '深黑主题theme-dark，浅色主题theme-light，深蓝主题theme-blue');
INSERT INTO `sys_config` VALUES (4, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '是否开启注册用户功能');
INSERT INTO `sys_config` VALUES (5, '用户管理-密码字符范围', 'sys.account.chrtype', '0', 'Y', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '默认任意字符范围，0任意（密码可以输入任意字符），1数字（密码只能为0-9数字），2英文字母（密码只能为a-z和A-Z字母），3字母和数字（密码必须包含字母，数字）,4字母数组和特殊字符（密码包含字母，数字，特殊字符-_）');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '部门状态（0正常 1停用）',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '无尤', 0, '无尤', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-10 05:36:32', '');
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '中国总公司', 1, '无尤', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-10 05:36:24', '');
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '美国分公司', 2, '无尤', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-10 05:36:32', '');
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '无尤', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-10 05:35:11', '');
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, '无尤', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-10 05:35:28', '');
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, '无尤', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-10 05:35:31', '');
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, '无尤', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-10 05:35:35', '');
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, '无尤', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-10 05:35:38', '');
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, '无尤', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-10 05:36:28', '');
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, '无尤', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-05-10 05:36:32', '');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '其他操作');
INSERT INTO `sys_dict_data` VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '新增操作');
INSERT INTO `sys_dict_data` VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '修改操作');
INSERT INTO `sys_dict_data` VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '删除操作');
INSERT INTO `sys_dict_data` VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '授权操作');
INSERT INTO `sys_dict_data` VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '导出操作');
INSERT INTO `sys_dict_data` VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '导入操作');
INSERT INTO `sys_dict_data` VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '强退操作');
INSERT INTO `sys_dict_data` VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '生成操作');
INSERT INTO `sys_dict_data` VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '清空操作');
INSERT INTO `sys_dict_data` VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录状态列表');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` tinyint(1) NULL DEFAULT 1 COMMENT '是否并发执行（0允许 1禁止）',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', 1, 1, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', 1, 1, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', 1, 1, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `login_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录账号',
  `ipaddr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统访问记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '请求地址',
  `target` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
  `menu_type` tinyint(1) NULL DEFAULT NULL COMMENT '类型（1: 目录, 2: 菜单, 3: 按钮）',
  `visible` tinyint(1) NULL DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1062 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, '#', '', 1, 0, '', 'fa fa-gear', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:45', '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, '#', '', 1, 0, '', 'fa fa-video-camera', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:45', '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, '#', '', 1, 0, '', 'fa fa-bars', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:45', '系统工具目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, '/system/users', 'menuItem', 2, 0, 'system:user:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2020-06-19 03:17:01', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, '/system/roles', 'menuItem', 2, 0, 'system:role:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2020-06-20 03:34:53', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, '/system/menu', '', 2, 0, 'system:menu:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, '/system/dept', '', 2, 0, 'system:dept:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, '/system/post', '', 2, 0, 'system:post:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, '/system/dict', '', 2, 0, 'system:dict:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, '/system/config', '', 2, 0, 'system:config:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, '/system/notice', '', 2, 0, 'system:notice:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, '#', '', 1, 0, '', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:45', '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, '/monitor/online', '', 2, 0, 'monitor:online:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, '/monitor/job', '', 2, 0, 'monitor:job:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, '/monitor/data', '', 2, 0, 'monitor:data:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 3, '/monitor/server', '', 2, 0, 'monitor:server:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '表单构建', 3, 1, '/tool/build', '', 2, 0, 'tool:build:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '表单构建菜单');
INSERT INTO `sys_menu` VALUES (114, '代码生成', 3, 2, '/tool/gen', '', 2, 0, 'tool:gen:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '代码生成菜单');
INSERT INTO `sys_menu` VALUES (115, '系统接口', 3, 3, '/tool/swagger', '', 2, 0, 'tool:swagger:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, '/monitor/operlog', '', 2, 0, 'monitor:operlog:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, '/monitor/logininfor', '', 2, 0, 'monitor:logininfor:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:54', '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '#', '', 3, 0, 'system:user:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '#', '', 3, 0, 'system:user:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '#', '', 3, 0, 'system:user:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '#', '', 3, 0, 'system:user:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '#', '', 3, 0, 'system:user:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '#', '', 3, 0, 'system:user:import', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '#', '', 3, 0, 'system:user:resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '#', '', 3, 0, 'system:role:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '#', '', 3, 0, 'system:role:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '#', '', 3, 0, 'system:role:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '#', '', 3, 0, 'system:role:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '#', '', 3, 0, 'system:role:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '#', '', 3, 0, 'system:menu:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '#', '', 3, 0, 'system:menu:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '#', '', 3, 0, 'system:menu:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '#', '', 3, 0, 'system:menu:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1016, '部门查询', 103, 1, '#', '', 3, 0, 'system:dept:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1017, '部门新增', 103, 2, '#', '', 3, 0, 'system:dept:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1018, '部门修改', 103, 3, '#', '', 3, 0, 'system:dept:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1019, '部门删除', 103, 4, '#', '', 3, 0, 'system:dept:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1020, '岗位查询', 104, 1, '#', '', 3, 0, 'system:post:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1021, '岗位新增', 104, 2, '#', '', 3, 0, 'system:post:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1022, '岗位修改', 104, 3, '#', '', 3, 0, 'system:post:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1023, '岗位删除', 104, 4, '#', '', 3, 0, 'system:post:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1024, '岗位导出', 104, 5, '#', '', 3, 0, 'system:post:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, '#', '', 3, 0, 'system:dict:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, '#', '', 3, 0, 'system:dict:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, '#', '', 3, 0, 'system:dict:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, '#', '', 3, 0, 'system:dict:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, '#', '', 3, 0, 'system:dict:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, '#', '', 3, 0, 'system:config:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, '#', '', 3, 0, 'system:config:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, '#', '', 3, 0, 'system:config:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, '#', '', 3, 0, 'system:config:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, '#', '', 3, 0, 'system:config:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1035, '公告查询', 107, 1, '#', '', 3, 0, 'system:notice:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1036, '公告新增', 107, 2, '#', '', 3, 0, 'system:notice:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1037, '公告修改', 107, 3, '#', '', 3, 0, 'system:notice:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1038, '公告删除', 107, 4, '#', '', 3, 0, 'system:notice:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1039, '操作查询', 500, 1, '#', '', 3, 0, 'monitor:operlog:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1040, '操作删除', 500, 2, '#', '', 3, 0, 'monitor:operlog:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1041, '详细信息', 500, 3, '#', '', 3, 0, 'monitor:operlog:detail', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1042, '日志导出', 500, 4, '#', '', 3, 0, 'monitor:operlog:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1043, '登录查询', 501, 1, '#', '', 3, 0, 'monitor:logininfor:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1044, '登录删除', 501, 2, '#', '', 3, 0, 'monitor:logininfor:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1045, '日志导出', 501, 3, '#', '', 3, 0, 'monitor:logininfor:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1046, '账户解锁', 501, 4, '#', '', 3, 0, 'monitor:logininfor:unlock', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1047, '在线查询', 109, 1, '#', '', 3, 0, 'monitor:online:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1048, '批量强退', 109, 2, '#', '', 3, 0, 'monitor:online:batchForceLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1049, '单条强退', 109, 3, '#', '', 3, 0, 'monitor:online:forceLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1050, '任务查询', 110, 1, '#', '', 3, 0, 'monitor:job:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1051, '任务新增', 110, 2, '#', '', 3, 0, 'monitor:job:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1052, '任务修改', 110, 3, '#', '', 3, 0, 'monitor:job:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1053, '任务删除', 110, 4, '#', '', 3, 0, 'monitor:job:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1054, '状态修改', 110, 5, '#', '', 3, 0, 'monitor:job:changeStatus', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1055, '任务详细', 110, 6, '#', '', 3, 0, 'monitor:job:detail', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1056, '任务导出', 110, 7, '#', '', 3, 0, 'monitor:job:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1057, '生成查询', 114, 1, '#', '', 3, 0, 'tool:gen:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1058, '生成修改', 114, 2, '#', '', 3, 0, 'tool:gen:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1059, '生成删除', 114, 3, '#', '', 3, 0, 'tool:gen:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1060, '预览代码', 114, 4, '#', '', 3, 0, 'tool:gen:preview', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');
INSERT INTO `sys_menu` VALUES (1061, '生成代码', 114, 5, '#', '', 3, 0, 'tool:gen:code', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-03 00:14:59', '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` tinyint(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告内容',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 新版本发布啦', 2, '新版本内容', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2020-08-03 16:17:58', '超级管理员');
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 系统凌晨维护', 1, '维护内容', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2020-08-03 16:18:00', '超级管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` tinyint(1) NULL DEFAULT 0 COMMENT '业务类型（0其他 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` tinyint(1) NULL DEFAULT 0 COMMENT '操作类别（0其他 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (1, '操作日志', 9, 'com.wuyou.web.controller.monitor.SysOperlogController.clean()', 'POST', 1, 'admin', '研发部门', '/monitor/operlog/clean', '127.0.0.1', '内网 IP', '{ }', '{\r\n  \"msg\" : \"成功\",\r\n  \"code\" : 0\r\n}', 0, '', NULL);

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` tinyint(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` tinyint(1) NULL DEFAULT 1 COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` tinyint(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, 1, 0, 0, 'admin', '2018-03-16 11:33:00', 'ry', '2020-08-03 16:17:14', '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 3, 2, 0, 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-07-01 12:13:24', '普通角色');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 104);
INSERT INTO `sys_role_dept` VALUES (2, 105);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1000);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 1019);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 1021);
INSERT INTO `sys_role_menu` VALUES (2, 1022);
INSERT INTO `sys_role_menu` VALUES (2, 1023);
INSERT INTO `sys_role_menu` VALUES (2, 1024);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1035);
INSERT INTO `sys_role_menu` VALUES (2, 1036);
INSERT INTO `sys_role_menu` VALUES (2, 1037);
INSERT INTO `sys_role_menu` VALUES (2, 1038);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (4, 1);
INSERT INTO `sys_role_menu` VALUES (4, 100);
INSERT INTO `sys_role_menu` VALUES (4, 101);
INSERT INTO `sys_role_menu` VALUES (4, 102);
INSERT INTO `sys_role_menu` VALUES (4, 103);
INSERT INTO `sys_role_menu` VALUES (4, 104);
INSERT INTO `sys_role_menu` VALUES (4, 105);
INSERT INTO `sys_role_menu` VALUES (4, 106);
INSERT INTO `sys_role_menu` VALUES (4, 107);
INSERT INTO `sys_role_menu` VALUES (4, 108);
INSERT INTO `sys_role_menu` VALUES (4, 500);
INSERT INTO `sys_role_menu` VALUES (4, 501);
INSERT INTO `sys_role_menu` VALUES (4, 1000);
INSERT INTO `sys_role_menu` VALUES (4, 1001);
INSERT INTO `sys_role_menu` VALUES (4, 1002);
INSERT INTO `sys_role_menu` VALUES (4, 1003);
INSERT INTO `sys_role_menu` VALUES (4, 1004);
INSERT INTO `sys_role_menu` VALUES (4, 1005);
INSERT INTO `sys_role_menu` VALUES (4, 1006);
INSERT INTO `sys_role_menu` VALUES (4, 1007);
INSERT INTO `sys_role_menu` VALUES (4, 1008);
INSERT INTO `sys_role_menu` VALUES (4, 1009);
INSERT INTO `sys_role_menu` VALUES (4, 1010);
INSERT INTO `sys_role_menu` VALUES (4, 1011);
INSERT INTO `sys_role_menu` VALUES (4, 1012);
INSERT INTO `sys_role_menu` VALUES (4, 1013);
INSERT INTO `sys_role_menu` VALUES (4, 1014);
INSERT INTO `sys_role_menu` VALUES (4, 1015);
INSERT INTO `sys_role_menu` VALUES (4, 1016);
INSERT INTO `sys_role_menu` VALUES (4, 1017);
INSERT INTO `sys_role_menu` VALUES (4, 1018);
INSERT INTO `sys_role_menu` VALUES (4, 1019);
INSERT INTO `sys_role_menu` VALUES (4, 1020);
INSERT INTO `sys_role_menu` VALUES (4, 1021);
INSERT INTO `sys_role_menu` VALUES (4, 1022);
INSERT INTO `sys_role_menu` VALUES (4, 1023);
INSERT INTO `sys_role_menu` VALUES (4, 1024);
INSERT INTO `sys_role_menu` VALUES (4, 1025);
INSERT INTO `sys_role_menu` VALUES (4, 1026);
INSERT INTO `sys_role_menu` VALUES (4, 1027);
INSERT INTO `sys_role_menu` VALUES (4, 1028);
INSERT INTO `sys_role_menu` VALUES (4, 1029);
INSERT INTO `sys_role_menu` VALUES (4, 1030);
INSERT INTO `sys_role_menu` VALUES (4, 1031);
INSERT INTO `sys_role_menu` VALUES (4, 1032);
INSERT INTO `sys_role_menu` VALUES (4, 1033);
INSERT INTO `sys_role_menu` VALUES (4, 1034);
INSERT INTO `sys_role_menu` VALUES (4, 1035);
INSERT INTO `sys_role_menu` VALUES (4, 1036);
INSERT INTO `sys_role_menu` VALUES (4, 1037);
INSERT INTO `sys_role_menu` VALUES (4, 1038);
INSERT INTO `sys_role_menu` VALUES (4, 1039);
INSERT INTO `sys_role_menu` VALUES (4, 1040);
INSERT INTO `sys_role_menu` VALUES (4, 1041);
INSERT INTO `sys_role_menu` VALUES (4, 1042);
INSERT INTO `sys_role_menu` VALUES (4, 1043);
INSERT INTO `sys_role_menu` VALUES (4, 1044);
INSERT INTO `sys_role_menu` VALUES (4, 1045);
INSERT INTO `sys_role_menu` VALUES (4, 1046);
INSERT INTO `sys_role_menu` VALUES (9, 2);
INSERT INTO `sys_role_menu` VALUES (9, 3);
INSERT INTO `sys_role_menu` VALUES (9, 110);
INSERT INTO `sys_role_menu` VALUES (9, 113);
INSERT INTO `sys_role_menu` VALUES (9, 114);
INSERT INTO `sys_role_menu` VALUES (9, 115);
INSERT INTO `sys_role_menu` VALUES (9, 1050);
INSERT INTO `sys_role_menu` VALUES (9, 1051);
INSERT INTO `sys_role_menu` VALUES (9, 1052);
INSERT INTO `sys_role_menu` VALUES (9, 1053);
INSERT INTO `sys_role_menu` VALUES (9, 1054);
INSERT INTO `sys_role_menu` VALUES (9, 1055);
INSERT INTO `sys_role_menu` VALUES (9, 1056);
INSERT INTO `sys_role_menu` VALUES (9, 1057);
INSERT INTO `sys_role_menu` VALUES (9, 1058);
INSERT INTO `sys_role_menu` VALUES (9, 1059);
INSERT INTO `sys_role_menu` VALUES (9, 1060);
INSERT INTO `sys_role_menu` VALUES (9, 1061);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户 01注册用户）',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` tinyint(1) NULL DEFAULT 0 COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '盐加密',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '无尤', '00', 'ry@qq.com', '15888888888', 0, '', 'b89f0f06d57a122ec1acabdacfcab9e6', '350483', 0, 0, '127.0.0.1', '2020-08-03 15:18:20', 'admin', '2020-04-22 10:21:00', 'ry', '2020-08-03 15:18:20', '管理员');
INSERT INTO `sys_user` VALUES (2, 105, 'ry', '无尤', '00', 'ry@qq.com', '15666666666', 1, '', '3654ce1cb528fd6c160eed068c4f1709', '4bdcb6', 0, 0, '127.0.0.1', '2020-05-03 16:21:48', 'admin', '2020-04-22 10:21:00', 'admin', '2020-06-19 10:14:14', '测试员');

-- ----------------------------
-- Table structure for sys_user_online
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_online`;
CREATE TABLE `sys_user_online`  (
  `session_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户会话id',
  `login_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录账号',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `ipaddr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
  `start_timestamp` datetime(0) NULL DEFAULT NULL COMMENT 'session创建时间',
  `last_access_time` datetime(0) NULL DEFAULT NULL COMMENT 'session最后访问时间',
  `expire_time` int(5) NULL DEFAULT 0 COMMENT '超时时间，单位为分钟',
  PRIMARY KEY (`session_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '在线用户记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_online
-- ----------------------------
INSERT INTO `sys_user_online` VALUES ('add3048b-edef-4030-aab5-786bbcefeea2', 'admin', '研发部门', '127.0.0.1', '内网 IP', 'Chrome 8', 'Windows 10', 'on_line', '2020-08-03 15:18:12', '2020-08-03 15:19:20', 1800000);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (0, 4);
INSERT INTO `sys_user_post` VALUES (3, 4);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (0, 2);
INSERT INTO `sys_user_role` VALUES (3, 2);
INSERT INTO `sys_user_role` VALUES (4, 2);

SET FOREIGN_KEY_CHECKS = 1;
