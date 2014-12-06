-- --------------------------------------------------------
-- 主机:                           172.16.150.209
-- 服务器版本:                        5.5.29 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  8.2.0.4675
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 ctrip_testdb 的数据库结构
DROP DATABASE IF EXISTS `ctrip_testdb`;
CREATE DATABASE IF NOT EXISTS `ctrip_testdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ctrip_testdb`;


-- 导出  表 ctrip_testdb.group_tb 结构
DROP TABLE IF EXISTS `group_tb`;
CREATE TABLE IF NOT EXISTS `group_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_name` (`group_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  ctrip_testdb.group_tb 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `group_tb` DISABLE KEYS */;
INSERT INTO `group_tb` (`id`, `group_name`) VALUES
	(1, 'android'),
	(2, 'iOS'),
	(3, '数据组');
/*!40000 ALTER TABLE `group_tb` ENABLE KEYS */;


-- 导出  表 ctrip_testdb.role_tb 结构
DROP TABLE IF EXISTS `role_tb`;
CREATE TABLE IF NOT EXISTS `role_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(200) DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index 2` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  ctrip_testdb.role_tb 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `role_tb` DISABLE KEYS */;
INSERT INTO `role_tb` (`id`, `role_name`) VALUES
	(3, '开发人员'),
	(2, '管理员'),
	(1, '超级管理员');
/*!40000 ALTER TABLE `role_tb` ENABLE KEYS */;


-- 导出  表 ctrip_testdb.user_tb 结构
DROP TABLE IF EXISTS `user_tb`;
CREATE TABLE IF NOT EXISTS `user_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `user_pwd` varchar(255) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`),
  KEY `fk_user_group_id` (`group_id`),
  KEY `fk_user_role_id` (`role_id`),
  CONSTRAINT `fk_group_id` FOREIGN KEY (`group_id`) REFERENCES `group_tb` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_user_group_id` FOREIGN KEY (`group_id`) REFERENCES `group_tb` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_id` FOREIGN KEY (`role_id`) REFERENCES `role_tb` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 正在导出表  ctrip_testdb.user_tb 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `user_tb` DISABLE KEYS */;
INSERT INTO `user_tb` (`id`, `user_name`, `user_pwd`, `group_id`, `role_id`) VALUES
	(2, 'jgzhu', '1234', 1, 3),
	(3, 'jgzhu2', '1234', 3, 3),
	(4, 'cxb', '123', 3, 1);
/*!40000 ALTER TABLE `user_tb` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
