<p align="center" style="margin-left: 20px;">
	<img alt="logo" src="https://gitee.com/my-images/typora-imgs/raw/master/%E5%93%86%E5%95%A6a%E6%A2%A6test.png" width="80" height="80">
</p>
<h1 align="center" style="margin-top: 0;">Ginyi</h1>
<h5 align="center">基于对优秀开源项目RuoYi-Vue的喜爱，决定重构若依，打造属于自己的快速开发平台！</h5>
<p align="right">————向优秀致敬，向榜样学习！</p>
<p align="center">
	<a href="https://gitee.com/y_project/RuoYi-Vue/stargazers"><img src="https://img.shields.io/badge/license-MIT-brightgreen"></a>
	<a href="https://gitee.com/y_project/RuoYi-Vue"><img src="https://img.shields.io/badge/Ginyi-v1.0.0-red"></a>
	<a href="https://gitee.com/y_project/RuoYi-Vue/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
</p>



### 平台简介

基于`SpringBoot`和`Vue3`，搭配`Naive UI`组件库，模块清晰，代码规范，易读易上手。界面美观，可自主选择主题色，提高视觉舒适度！部分功能尚未实现，有时间将持续更新，欢迎Star⭐!



### 特别鸣谢

- [RuoYi-Vue](https://gitee.com/y_project/RuoYi-Vue?_from=gitee_search)
- [Naive UI](https://www.naiveui.com/)



### 技术选型

| 前端                                     | 后端                            |
| ---------------------------------------- | ------------------------------- |
| `Node` 项目环境基础                      | `Maven` 构建多模块项目          |
| `Vite4` 构建项目                         | `Springboot` 项目主框架         |
| `Vue3` 项目主框架                        | `MySQL` 关系型数据库            |
| `TypeScript` 全量TS编写，搭配TSX模板渲染 | `MongoDB` 文档型数据库          |
| `Naive UI` 组件库                        | `Redis` 基于内存数据库          |
| `Pinia` 全局状态管理                     | `SpringSecurity & JWT` 权限框架 |
| `Axios` 网络请求库                       | `MyBatis-Plus` ORM框架          |
| `Monaco` 代码编辑器（vscode）            | `Hutool` 工具类                 |
| `...`                                    | `...`                           |

**适用人群：**

1. 有`Vue3`、`TypeScript`基础
2. 有`Springboot`基础，对`Maven多模块`有一定的了解



### 内置功能

| 功能         | 介绍                                                         | Ruoyi | Ginyi |
| ------------ | ------------------------------------------------------------ | ----- | ----- |
| `用户管理`   | 用户是系统操作者，该功能主要完成系统用户配置                 | ⭕     | ⭕     |
| `部门管理`   | 配置系统组织机构（公司、部门、小组），树结构展现支持数据权限 | ⭕     | ⭕     |
| `岗位管理`   | 配置系统用户所属担任职务                                     | ⭕     | ⭕     |
| `菜单管理`   | 配置系统菜单，操作权限，按钮权限标识等                       | ⭕     | ⭕     |
| `角色管理`   | 角色菜单权限分配、设置角色按机构进行数据范围权限划分         | ⭕     | ⭕     |
| `字典管理`   | 对系统中经常使用的一些较为固定的数据进行维护                 | ⭕     | ❎     |
| `参数管理`   | 对系统动态配置常用参数                                       | ⭕     | ❎     |
| `通知公告`   | 系统通知公告信息发布维护                                     | ⭕     | ❎     |
| `操作日志`   | 系统正常操作日志记录和查询；系统异常信息日志记录和查询       | ⭕     | ⭕     |
| `登录日志`   | 系统登录日志记录查询包含登录异常                             | ⭕     | ⭕     |
| `在线用户`   | 当前系统中活跃用户状态监控                                   | ⭕     | ⭕     |
| `定时任务`   | 在线（添加、修改、删除)任务调度包含执行结果日志              | ⭕     | ❎     |
| `代码生成`   | 前后端代码的生成（java、html、xml、sql）支持CRUD下载         | ⭕     | ❎     |
| `系统接口`   | 根据业务代码自动生成相关的api接口文档                        | ⭕     | ❎     |
| `服务监控`   | 监视当前系统CPU、内存、磁盘、堆栈等相关信息                  | ⭕     | ❎     |
| `缓存监控`   | 对系统的缓存信息查询，命令统计等                             | ⭕     | ❎     |
| `连接池监视` | 监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈  | ⭕     | ❎     |

**备注：**

- 尚未实现的功能，有时间将继续更新！



