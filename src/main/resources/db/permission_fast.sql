/*
Navicat MySQL Data Transfer

Source Server         : root-link
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : permission_fast

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-12-08 23:23:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL,
  `username` varchar(50) DEFAULT '' COMMENT '用户名',
  `operation` varchar(50) DEFAULT '' COMMENT '用户操作',
  `method` varchar(200) DEFAULT '' COMMENT '请求方法',
  `params` varchar(5000) DEFAULT '' COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时间',
  `ip` varchar(64) DEFAULT NULL COMMENT '用户ip',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1071409081350987778', 'root', '修改用户', 'com.doraro.permission_fast.controller.SysUserController.updateUser()', '[{\"userId\":1071408991639019521,\"status\":1}]', '28', '0:0:0:0:0:0:0:1', '2018-12-08 14:20:05');
INSERT INTO `sys_log` VALUES ('1071410560388407297', 'root', '删除用户', 'com.doraro.permission_fast.controller.SysUserController.createUser()', '[[1071408991639019521]]', '29', '0:0:0:0:0:0:0:1', '2018-12-08 14:25:58');
INSERT INTO `sys_log` VALUES ('1071422721290407938', 'root', '删除角色', 'com.doraro.permission_fast.controller.SysRoleController.deleteRole()', '[[1]]', '64', '0:0:0:0:0:0:0:1', '2018-12-08 15:14:15');

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `module_id` bigint(20) NOT NULL COMMENT '模块id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父模块id',
  `module_sname` varchar(50) NOT NULL DEFAULT '' COMMENT '模块中文释义',
  `perms` varchar(500) NOT NULL DEFAULT '' COMMENT '权限',
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_module
-- ----------------------------
INSERT INTO `sys_module` VALUES ('1', null, '角色管理', '');
INSERT INTO `sys_module` VALUES ('2', '1', '角色创建', 'sys_role:create');
INSERT INTO `sys_module` VALUES ('3', '1', '角色删除', 'sys_role:delete');
INSERT INTO `sys_module` VALUES ('4', '1', '角色查看', 'sys_role:view');
INSERT INTO `sys_module` VALUES ('5', '1', '角色修改', 'sys_role:update');
INSERT INTO `sys_module` VALUES ('6', null, '系统日志', '');
INSERT INTO `sys_module` VALUES ('7', '6', '日志查看', 'sys_log:view');
INSERT INTO `sys_module` VALUES ('8', null, '用户管理', '');
INSERT INTO `sys_module` VALUES ('9', '8', '用户创建', 'sys_user:create');
INSERT INTO `sys_module` VALUES ('10', '8', '用户查看', 'sys_user:view');
INSERT INTO `sys_module` VALUES ('11', '8', '用户修改', 'sys_user:update');
INSERT INTO `sys_module` VALUES ('12', '8', '用户删除', 'sys_user:delete');

-- ----------------------------
-- Table structure for sys_module_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_module_role`;
CREATE TABLE `sys_module_role` (
  `id` bigint(20) NOT NULL,
  `module_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_module_role
-- ----------------------------
INSERT INTO `sys_module_role` VALUES ('1', '2', '2', '2018-12-02 19:53:52', '2018-12-02 19:53:52');
INSERT INTO `sys_module_role` VALUES ('2', '7', '2', '2018-12-02 19:54:19', '2018-12-02 19:54:22');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名',
  `comment` varchar(100) NOT NULL DEFAULT '' COMMENT '角色备注',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建者用户id',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`),
  KEY `idx_rolename` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '超级管理员', '1', '2018-12-02 18:04:25');
INSERT INTO `sys_role` VALUES ('2', 'test', '测试角色', '1', '2018-12-02 19:50:06');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL COMMENT 'user主键',
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '用户密码',
  `salt` varchar(20) NOT NULL DEFAULT '' COMMENT '盐',
  `mail` varchar(100) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `status` tinyint(4) NOT NULL COMMENT '0-未激活 1-正常 2-禁用',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建者id',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idx_username` (`username`) USING BTREE,
  UNIQUE KEY `idx_email` (`mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'root', 'c9122db32dedd82992ebe9aa6009cf0c1311bb53298790fbd66c4f5e21cb9207', 'salt', 'test@qq.com', '1', '1', '2018-11-29 21:45:15');
INSERT INTO `sys_user` VALUES ('2', 'test', 'c9122db32dedd82992ebe9aa6009cf0c1311bb53298790fbd66c4f5e21cb9207', 'salt', 'test2@qq.com', '1', '1', '2018-12-02 19:31:00');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '用户主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2', '2');
INSERT INTO `sys_user_role` VALUES ('3', '1', '2');
