/*
 Navicat Premium Data Transfer

 Source Server         : Ubuntu -  MongoDB
 Source Server Type    : MongoDB
 Source Server Version : 50005
 Source Host           : localhost:27017
 Source Schema         : ginyi-spring-vue

 Target Server Type    : MongoDB
 Target Server Version : 50005
 File Encoding         : 65001

 Date: 22/12/2022 11:52:21
*/


// ----------------------------
// Collection structure for sys_log_login
// ----------------------------
db.getCollection("sys_log_login").drop();
db.createCollection("sys_log_login");

// ----------------------------
// Documents of sys_log_login
// ----------------------------
db.getCollection("sys_log_login").insert([ {
    _id: ObjectId("63a3111ee84a76394636da3a"),
    userName: "ginyi",
    status: "0",
    ipaddr: "192.168.0.100",
    loginLocation: "内网IP",
    browser: "Chrome 10",
    os: "Windows 10",
    msg: "登录成功",
    createTime: ISODate("2022-12-21T13:58:53.847Z"),
    _class: "ginyi.system.domain.SysLogLogin"
} ]);

// ----------------------------
// Collection structure for sys_log_operation
// ----------------------------
db.getCollection("sys_log_operation").drop();
db.createCollection("sys_log_operation");

// ----------------------------
// Documents of sys_log_operation
// ----------------------------
db.getCollection("sys_log_operation").insert([ {
    _id: ObjectId("63a31132e84a76394636da3b"),
    title: "菜单模块",
    businessType: NumberInt("1"),
    method: "ginyi.server.admin.controller.SysMenuController.add()",
    requestMethod: "POST",
    operatorType: NumberInt("1"),
    operationName: "ginyi",
    operationUrl: "/api/menu/add",
    operationIp: "192.168.0.100",
    operationLocation: "内网IP",
    operationParam: "{\"component\":\"/abc/bcd\",\"createBy\":\"ginyi\",\"createTime\":\"2022-12-21 21:59:14.327\",\"icon\":\"\",\"isCache\":\"1\",\"isFrame\":\"1\",\"menuId\":0,\"menuName\":\"测试菜单的子菜单eee\",\"menuType\":\"C\",\"params\":{},\"parentId\":2000,\"path\":\"/test\",\"perms\":\"\",\"query\":\"\",\"remark\":\"\",\"sort\":1,\"status\":\"0\",\"updateBy\":\"\",\"visible\":\"0\"}",
    jsonResult: "{\"code\":200,\"msg\":\"操作成功\"}",
    status: NumberInt("0"),
    _class: "ginyi.system.domain.SysLogOperation"
} ]);
