/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : yonder

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2015-03-20 17:24:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `menu_id_` varchar(64) NOT NULL,
  `parent_id_` varchar(64) DEFAULT NULL,
  `level_` int(11) DEFAULT NULL,
  `text_` varchar(50) DEFAULT NULL,
  `href_url_` varchar(100) DEFAULT NULL,
  `target_` varchar(20) DEFAULT NULL,
  `sort_` varchar(10) DEFAULT NULL,
  `icon_` varchar(20) DEFAULT NULL,
  `title_` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`menu_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('100000', null, '0', '权限管理', null, null, '100000', 'leftico01.png', null);
INSERT INTO `t_menu` VALUES ('100001', '100000', '1', '用户管理', 'web/admin/user/index', 'rightFrame', '100001', null, null);
INSERT INTO `t_menu` VALUES ('100002', '100000', '1', '角色管理', 'web/admin/role/index', 'rightFrame', '100002', null, null);
INSERT INTO `t_menu` VALUES ('300000', null, '0', '系统管理', null, null, '300000', 'leftico01.png', null);
INSERT INTO `t_menu` VALUES ('300001', '300000', '1', '系统参数设置', 'web/admin/system/param/index', 'rightFrame', '300001', null, null);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id_` varchar(64) NOT NULL,
  `name_` varchar(50) DEFAULT NULL,
  `description_` varchar(100) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL,
  `modify_time_` datetime DEFAULT NULL,
  `user_id_` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`role_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('100000', '超级管理员', '可管理后台整个系统', '2014-12-10 17:36:33', '2014-12-10 17:36:36', null);

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `role_id_` varchar(64) NOT NULL,
  `menu_id_` varchar(64) NOT NULL,
  PRIMARY KEY (`role_id_`,`menu_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('100000', '100000');
INSERT INTO `t_role_menu` VALUES ('100000', '100001');
INSERT INTO `t_role_menu` VALUES ('100000', '100002');
INSERT INTO `t_role_menu` VALUES ('100000', '300000');
INSERT INTO `t_role_menu` VALUES ('100000', '300001');

-- ----------------------------
-- Table structure for t_server_user
-- ----------------------------
DROP TABLE IF EXISTS `t_server_user`;
CREATE TABLE `t_server_user` (
  `user_id_` varchar(64) NOT NULL,
  `username_` varchar(50) DEFAULT NULL,
  `assistant_password_` varchar(20) DEFAULT NULL,
  `encrypted_password_` varchar(50) DEFAULT NULL,
  `name_` varchar(50) DEFAULT NULL,
  `phone_` varchar(20) DEFAULT NULL,
  `parent_id_` varchar(64) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL,
  `modify_time_` datetime DEFAULT NULL,
  `last_login_time_` datetime DEFAULT NULL,
  `is_delete_` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_server_user
-- ----------------------------
INSERT INTO `t_server_user` VALUES ('402881e846e4b3910146e4b8ce6c0004', 'admin', 'T0tQNVM=', '6cff5807cf61610da2446669acd09838', 'yonder', '18559785980', null, '2014-12-10 11:11:11', '2014-12-10 11:11:11', '2015-03-20 17:14:48', '0');

-- ----------------------------
-- Table structure for t_system_param
-- ----------------------------
DROP TABLE IF EXISTS `t_system_param`;
CREATE TABLE `t_system_param` (
  `param_id_` varchar(20) NOT NULL,
  `name_` varchar(50) DEFAULT NULL,
  `value_` varchar(100) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL,
  `modify_time_` datetime DEFAULT NULL,
  PRIMARY KEY (`param_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_system_param
-- ----------------------------
INSERT INTO `t_system_param` VALUES ('balanceAkkaIp', '负载AKKA通信IP', '192.168.2.15', '2014-12-18 09:34:22', '2014-12-18 15:45:05');
INSERT INTO `t_system_param` VALUES ('balanceAkkaPort', '负载AKKA通信端口', '10001', '2014-12-18 09:34:22', '2014-12-18 09:34:22');
INSERT INTO `t_system_param` VALUES ('initPassword', '新用户初始化密码', '123456', '2014-12-18 09:34:22', '2014-12-18 09:42:22');
INSERT INTO `t_system_param` VALUES ('worldAkkaIp', '世界服AKKA通信IP', '', '2014-12-18 09:34:22', '2014-12-18 09:34:22');
INSERT INTO `t_system_param` VALUES ('worldAkkaPort', '世界服AKKA通信端口', '', '2014-12-18 09:34:22', '2014-12-18 09:34:22');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_id_` varchar(64) NOT NULL,
  `role_id_` varchar(64) NOT NULL,
  PRIMARY KEY (`role_id_`,`user_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('402881e846e4b3910146e4b8ce6c0004', '100000');
