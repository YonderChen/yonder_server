/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : localhost
 Source Database       : demo

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : utf-8

 Date: 10/28/2014 09:56:32 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `game_param`
-- ----------------------------
DROP TABLE IF EXISTS `game_param`;
CREATE TABLE `game_param` (
  `param_` varchar(200) NOT NULL COMMENT '参数名',
  `value_` varchar(2000) DEFAULT NULL COMMENT '参数值',
  `desc_` varchar(200) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`param_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='游戏参数表，不同于游戏配置表，区服级别的参数配置';

-- ----------------------------
--  Records of `game_param`
-- ----------------------------
INSERT INTO `game_param` VALUES ('language', 'tw', '默认语言');
INSERT INTO `game_param` VALUES ('beginOfGpId', '10000100', '角色id，当前区服的开始值');
INSERT INTO `game_param` VALUES ('endOfGpId', '10499999', '角色id，当前区服的终止值');
INSERT INTO `game_param` VALUES ('areaId', '1000', '区服编号');
INSERT INTO `game_param` VALUES ('timeZone', '8', '时区设置');
INSERT INTO `game_param` VALUES ('areaName', '开发服', '区服名称');
INSERT INTO `game_param` VALUES ('keyOfSign', 'abc@@&&&sdfseeeeeiuiu', '服务器与客户端数字签名校验使用的密钥');
INSERT INTO `game_param` VALUES ('jmsUrl', 'tcp://192.168.2.15:61616?connectionTimeout=5000', 'jms服务器url');
INSERT INTO `game_param` VALUES ('jmsUser', 'system', 'jms用户名');
INSERT INTO `game_param` VALUES ('jmsPassword', '', 'jms连接密码');
INSERT INTO `game_param` VALUES ('minaPort', '1234', '本地mina端口');
INSERT INTO `game_param` VALUES ('akkaPort', '10000', '本地akka端口');
INSERT INTO `game_param` VALUES ('akkaWorldServerIp', '192.168.2.15', '世界服Akka内网IP');
INSERT INTO `game_param` VALUES ('akkaWorldServerPort', '10002', '世界服Akka内网端口');
INSERT INTO `game_param` VALUES ('akkaBalanceServerPort', '10001', '负载均衡Akka内网端口');
INSERT INTO `game_param` VALUES ('akkaBalanceServerIp', '192.168.2.15', '负载均衡Akka内网IP');
INSERT INTO `game_param` VALUES ('configDownloadType', '0', '配置文件同步方式。0：区服socket直接同步，1：配置服务器http下载');
INSERT INTO `game_param` VALUES ('configDownloadUrl', '', '配置文件http下载基础路径');
INSERT INTO `game_param` VALUES ('configSyncShellCmd', '', '同步配置文件shell命令');

