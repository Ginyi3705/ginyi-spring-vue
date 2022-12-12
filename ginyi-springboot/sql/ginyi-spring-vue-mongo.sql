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

 Date: 12/12/2022 23:55:25
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
    _id: ObjectId("63974c1404828c5162cd4e46"),
    userName: "ginyi",
    status: "0",
    ipaddr: "192.168.0.101",
    loginLocation: "内网IP",
    browser: "Chrome 10",
    os: "Windows 10",
    msg: "登录成功",
    _class: "ginyi.system.domain.SysLogininfor"
} ]);
db.getCollection("sys_log_login").insert([ {
    _id: ObjectId("63974c9a04828c5162cd4e47"),
    userName: "ginyi",
    status: "1",
    ipaddr: "192.168.0.101",
    loginLocation: "内网IP",
    browser: "Chrome 10",
    os: "Windows 10",
    msg: "用户名密码不匹配",
    _class: "ginyi.system.domain.SysLogininfor"
} ]);
db.getCollection("sys_log_login").insert([ {
    _id: ObjectId("63974d0e04828c5162cd4e48"),
    userName: "admin",
    status: "0",
    ipaddr: "192.168.0.101",
    loginLocation: "内网IP",
    browser: "Chrome 10",
    os: "Windows 10",
    msg: "登录成功",
    _class: "ginyi.system.domain.SysLogininfor"
} ]);

// ----------------------------
// Collection structure for sys_log_operation
// ----------------------------
db.getCollection("sys_log_operation").drop();
db.createCollection("sys_log_operation");

// ----------------------------
// Documents of sys_log_operation
// ----------------------------
