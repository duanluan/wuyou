## 简介

[若依](https://gitee.com/y_project/RuoYi)在 Gitee 上的 Star 比较多，但是许多地方的代码不是很复合我的规范，于是对其进行优化，以供自己和感兴趣的你使用。

做了哪些修改，为什么这样修改，会详尽的记录，改动较大的会记录在 Wiki 当中，如果有任何问题欢迎提出，但我不一定会改。

会同步更新 Gitee 上 RuoYi 的 Commits，提交格式默认为 “fork(*): 2020-04-23 cb32d5c”。

## 开发设计规范

所有优化参考此规范，如果有任何一项感觉不合适，欢迎提出，但我不一定会改。

### 结构

### 数据库

### 代码

## 优化项部分内容

| 名称 | 内容 | 进度 | 备注 |
| --- | --- | --- | --- |
| 代码格式 | IDEA 默认的代码格式化。缩进修改为两个空格。HTMl 中的 style 标签和 script 标签中的内容顶头开始。修改 Service 和 Mapper 的方法名。 | 100% | [我的 Intellij IDEA code style XML 文件](https://github.com/csaarg/csaarg-ruoyi/blob/master/docs/%5BIntellij%20IDEA%20code%20style%5D%20nowrap.xml) |
| MyBatis Mapper XML | 类使用全路径免除配置 typeAliasesPackage，避免 IDEA 报错，方便提示。删除 parameterType 属性。 | 100% |  |
| Java 代码结构 | 添加 Lombok，修改所有实体类，LoggerFactory.getLogger 替换为 @Slf4j。调整注解顺序：@RequestMapping < @ResponseBody < @Tranactional < @RequiresPermissions < @Log，@NotNull/NotBlank < @Size < …… < @Excel 等等。 | 100% |  |
| static 目录结构 | 所有第三方依赖全部放到 lib 目录下 | 100% |  |
| 集成 MyBatis Plus | 替换 Mapper XML 中的单表操作方法。替换分页插件。替换多数据源。 | 100% | [MyBatis-Plus](https://mp.baomidou.com/) |
| 阿里编码规约及代码优化 | 使用 Alibaba Java Coding Guidelines 扫描并解决。 | 剩余 59 - 13 |  |
| 前端枚举 | 前端状态、类型等字段使用枚举。 | 0% |  |
| 前端新增页面和编辑页面合一 | 前端的新增页面合并到编辑页面中，避免重复修改。 | 10% |  |
| 接口修改为 RESTful 规范 | …… | 0% |  |
| 修改代码生成模板 | …… | 0% |  |
| …… |  |  |  |
