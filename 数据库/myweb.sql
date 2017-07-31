/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : myweb

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 07/24/2017 23:39:30 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `xfsy_diary`
-- ----------------------------
DROP TABLE IF EXISTS `xfsy_diary`;
CREATE TABLE `xfsy_diary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` varchar(15) DEFAULT NULL COMMENT '发表时间',
  `place` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT 'CHN' COMMENT '地点',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '日记内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='日记表';

-- ----------------------------
--  Table structure for `xfsy_link`
-- ----------------------------
DROP TABLE IF EXISTS `xfsy_link`;
CREATE TABLE `xfsy_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '网站名字',
  `http` varchar(255) NOT NULL COMMENT '网站地址',
  `weight` int(11) DEFAULT '0' COMMENT '权重',
  `time` varchar(15) DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='友情链接表';

-- ----------------------------
--  Table structure for `xfsy_message`
-- ----------------------------
DROP TABLE IF EXISTS `xfsy_message`;
CREATE TABLE `xfsy_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `contact` varchar(100) DEFAULT NULL COMMENT '联系方式',
  `address` varchar(255) DEFAULT NULL COMMENT '个人网站',
  `time` varchar(15) NOT NULL COMMENT '留言时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='留言表';

SET FOREIGN_KEY_CHECKS = 1;
