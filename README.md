# Web&SOA-project
Tongji University Software Engineer Project

### 开发环境
```
jdk:1.8
IDE:IDEA
微服务架构
```

### 项目结构
```
工具类放置于common模块中
     接口文档配置在swagger中
```

```
src-------|main--------|java---------|------controller                     #控制类
   |           |                     |
   |           |                     |------error                          #错误 
   |           |                     |
   |           |                     |------model                          #实体类
   |           |                     |
   |           |                     |------repository                     #DAO层 
   |           |                     |
   |           |                     |------service                        #服务层
   |           |                     |
   |           |                     |------utils                          #工具类
   |           |                     |   
   |           |                     |------SEbackEndApplication           #项目入口
   |           |
   |           |-------|resources----|------static
   |                                 |   
   |                                 |------temlates
   |                                 |   
   |                                 |------application.properties
   |
   |
   |
   |
   |------|test--------|java
               |
               |-------|testHTTP             #HTTP测试
```

### 技术栈
```
front-end--------------------|vue
         |
         |-------------------|ElementUI

back-end---------------------|SpringBoot
        |
        |--------------------|Mysql
        |
        |--------------------|Maven

部署：centOS7
```

### 响应信息示例
```
"timestamp": "",        #时间戳
  "data": {}            #数据
  "success": true,      #成功与否
  "message": null,      #提示信息
  "code": 0             #成功为0
```
#### 返回码定义
```
成功返回码---------------0
用户密码错误返回码--------1
用户已存在返回码----------2
查找失败返回码 --——-------3
```
