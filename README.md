# 社团管理系统

一个学校的创新课题，sql文件测试数据不多，需要自行添加

---

### 运行环境

* JDK 1.8编写
* 前端 VSCode1.89.1 Windows下
* 后端 IntelliJ IDEA 2023.3.2 Windwos下
* apache-maven-3.5.4
* MySQL-5.7.35



### 使用技术

* Java Spring boot
* Vue3、CSS、TypeScript
* MySQL
* 封装Page类
* 前端使用多采用Element进行创作，TS用于接口编写，保证前后端分离



### 实现功能

- 前端展示
  - 普通用户登录、注册。保证用户名唯一性，不可重复。
  - 登录前可以查看各种分类的社团的概要，简介等信息，还有最近各社团将要举行的各种活动的时间地点，活动标题等活动公告。
  - 登录后可以查看、修改个人信息、密码。以及线上报名社团及社团发布的活动。
- 管理员登录
  - 用户管理，添加用户(根据账号修改普通用户的密码或权限)，更新当前账号密码、查看用户列表(可以查看用户详细信息及加入或创建的社团信息，可对用户的个人信息进行修改、删除)。
  - 社团管理，添加社团，查看社团列表(可以查看某一个社团的具体信息，进行编辑或删除)，查看社团活动信息(可以查看社团活动详情，进行编辑或删除)。
  - 审核管理，创建社团审批，创办活动审批，创立社团新闻详情
  - 活动报名人员导出Excel，方便查看人员情况。



###  运行截图

**登录界面 **

![bc12c4c01d225bceea65bc8da2e3491](D:\Code\Community Management System\img\bc12c4c01d225bceea65bc8da2e3491.png)



**注册界面**

![093319f30b1107f3c320300f3ce07dc](D:\Code\Community Management System\img\093319f30b1107f3c320300f3ce07dc.png)



**前端首页界面**

![6be7e366e33110ff2b5ba601a656577](D:\Code\Community Management System\img\6be7e366e33110ff2b5ba601a656577.png)



**社团列表**

![96d2f93ebd51d370bb0af270e8fbe22](D:\Code\Community Management System\img\96d2f93ebd51d370bb0af270e8fbe22.png)



**活动列表**

![e1bc8a4ed10328c9fd8b815dd3e9271](D:\Code\Community Management System\img\e1bc8a4ed10328c9fd8b815dd3e9271.png)



**活动详情展示**

![a34d7ee76d0e79bf9224983311cb00c](D:\Code\Community Management System\img\a34d7ee76d0e79bf9224983311cb00c.png)



**后台数据首页**

![193d52ab6e05a5b4b941a8e1772a709](D:\Code\Community Management System\img\193d52ab6e05a5b4b941a8e1772a709.png)



**活动、新闻、公告审批**（可导出Excel）

![6c0c5e5fe0f58d89fc1aceb3aa0b434](D:\Code\Community Management System\img\6c0c5e5fe0f58d89fc1aceb3aa0b434.png)



**社团成员列表**（可导出Excel）

![33fc0acd2b47fc33ff18ce221d4205e](D:\Code\Community Management System\img\33fc0acd2b47fc33ff18ce221d4205e.png)



**权限分配列表**

![a633e57fd3bad6b893ea9084b1ad050](D:\Code\Community Management System\img\a633e57fd3bad6b893ea9084b1ad050.png)
