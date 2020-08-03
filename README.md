## 加入讨论

Microsoft Teams（全平台支持 50W 人加入的团队协作软件）：https://teams.microsoft.com/l/team/19%3a7e51c51538f94346bbcf81c27680fb24%40thread.tacv2/conversations?groupId=f5857b43-85e3-4e5f-9383-b8dad16e947c&tenantId=4e2b5c12-4bc2-48c5-85f3-fbcceb3a3088

## 简介

[若依](http://doc.wuyou.vip)在 Gitee 上的 Star 比较多，但是许多地方的代码不是很复合我的规范，于是对其进行优化，以供自己和感兴趣的你使用。

做了哪些修改，为什么这样修改，会详尽的记录，如果有任何问题欢迎提出，但我不一定会改。

会同步更新 Gitee 上 wuyou 的 Commits，提交格式默认为 “fork: 2020-04-23 cb32d5c ……”。

**现求一个好看的 Logo**

## 开发设计规范

所有优化参考此规范，如果有任何一项感觉不合适，欢迎提出，但我不一定会改。

### 结构

……

### 数据库

#### 命名规范

1. 【强制】表名及字段名全小写，单词之间下划线分隔。

2. 【推荐】表名以简要为主，不要添加无关前后缀。可以在表名前加上模块名。

    正例：room / sys_user | 反例：room_info / sys_user_info

4. 【推荐】字段名以简要为主，在无歧义的情况下在自身表中不要添加表名前缀，因为这样到前后端代码中才能更好地体现其逻辑，避免过度冗余。说明：在自身表中指的是 name 是自己的 name，在外键中自然要加表前缀了。

    正例：visitor.name | 反例：visitor.visitor_name
    
5. 【推荐】表之间有关联，在无歧义的情况下可以省略部分表前缀，避免过长。

    正例：dish_type_dish.type_id | 反例：dish_type_dish.dish_type_id
    
6. 【推荐】取名时不管是表名还是字段名都要多参考它的双语例句其词性。

    a. 含义：地址表（假设指的是楼栋当中的地址，比如某个卫生间）有多个翻译，region 指区域范围，area 指一片地方，position 多指地位，也指具体在某物和某物对比的位置，address 指某栋楼的地址，而不是楼栋内部，location 指地方的定位，综合下来，建议使用 location。又比如实际 XXX 这些字段，有 real 和 actual 两种翻译，前者指真实存在而不是虚幻的，后者指行为或事实上已经发生的事，所以应该用后者。

    b. 词性：“佛性”一点，单词要有过去、现在、未来，下面举一些例子 🌰。

      * 创建时间：created_time | create_time
      * 是否删除：deleted | delete_flag、is_delete
        
    c. 统一性：多张表的不同字段，含义相同，字段名也要相同。
    
      * 可数且有关联的状态：state
      * 是否 XXX：避免 is、flag 等前后缀，使用过去时
      * 微信 Open ID：wx_open_id
      * 实际开始时间：actual_start_time | real_start_time
    
7. 【推荐】设计字段时适量添加需要经常查询的冗余字段，同时注意主表更新时冗余字段也要更新。

8. 【推荐】索引、视图、函数、存储函数等命名规范，待补充。

### 代码

……

## 优化项部分内容

| 名称 | 内容 | 进度 | 备注 |
| --- | --- | --- | --- |
| 代码格式 | IDEA 默认的代码格式化。缩进修改为两个空格。HTMl 中的 style 标签和 script 标签中的内容顶头开始。修改 Service 和 Mapper 的方法名。 | 100% | [我的 Intellij IDEA code style XML 文件](./docs/%5BIntellij%20IDEA%20code%20style%5D%20nowrap.xml) |
| MyBatis Mapper XML | 类使用全路径免除配置 typeAliasesPackage，避免 IDEA 报错，方便提示。删除 parameterType 属性。 | 100% |  |
| Java 代码结构 | 添加 Lombok，修改所有实体类，LoggerFactory.getLogger 替换为 @Slf4j。调整注解顺序：@RequestMapping < @ResponseBody < @Tranactional < @RequiresPermissions < @Log，@NotNull/NotBlank < @Size < …… < @Excel 等等。 | 100% |  |
| static 目录结构 | 所有第三方依赖全部放到 lib 目录下 | 100% |  |
| 集成 [MyBatis Plus](https://mp.baomidou.com/) | 替换 Mapper XML 中的单表操作方法。替换分页插件。替换多数据源。 | 100% |  |
| 阿里编码规约及代码优化 | 使用 [Alibaba Java Coding Guidelines](https://plugins.jetbrains.com/plugin/10046-alibaba-java-coding-guidelines) 扫描并解决。 | 剩余 59 - 13 |  |
| 前端枚举 | 前端状态、类型等字段使用枚举。 | 0% |  |
| 前端新增页面和编辑页面合一 | 前端的新增页面合并到编辑页面中，避免重复修改。 | 10% |  |
| 接口修改为 RESTful 规范 | 因为是模板引擎，而且为了避免麻烦（比如页面不复数，接口复数），所以列出数据的接口是 \[GET\] XXX/list | 10% |  |
| 修改代码生成模板 | …… | 0% |  |
| 修改数据库列名和实体类属性名 | …… | 0% |  |
| Swagger 替换为 [knife4j](https://gitee.com/xiaoym/knife4j) | …… | 0% |  |
| 验证码替换为 [EasyCaptcha](https://github.com/whvcse/EasyCaptcha) | 更美观，写法更简单 | 100% |  |
