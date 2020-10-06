-- --------------------------------------------------------
-- 主机:                           192.168.1.80
-- 服务器版本:                        8.0.21 - MySQL Community Server - GPL
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  11.0.0.6099
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 tree 的数据库结构
DROP DATABASE IF EXISTS `tree`;
CREATE DATABASE IF NOT EXISTS `tree` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tree`;

-- 导出  表 tree.books 结构
DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_general_ci DEFAULT '',
  `created` bigint DEFAULT '0' COMMENT '创建时间',
  `updated` bigint DEFAULT '0' COMMENT '修改时间',
  `del_flg` tinyint(1) DEFAULT '0' COMMENT '逻辑标识，0-正常；1-删除；',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='书籍表';

-- 正在导出表  tree.books 的数据：~5 rows (大约)
DELETE FROM `books`;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` (`id`, `name`, `created`, `updated`, `del_flg`) VALUES
	(1, '飞狐外传', 0, 0, 0),
	(2, '雪山飞狐', 0, 0, 0),
	(3, '连城诀', 0, 0, 0),
	(4, '天龙八部', 0, 0, 0),
	(5, '射雕英雄传', 0, 0, 0);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;

-- 导出  表 tree.comments 结构
DROP TABLE IF EXISTS `comments`;
CREATE TABLE IF NOT EXISTS `comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bid` int NOT NULL DEFAULT '0' COMMENT '书籍ID',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '评论内容',
  `created` bigint DEFAULT '0' COMMENT '创建时间',
  `updated` bigint DEFAULT '0' COMMENT '修改时间',
  `del_flg` tinyint(1) DEFAULT '0' COMMENT '逻辑标识，0-正常；1-删除；',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='评论表';

-- 正在导出表  tree.comments 的数据：~0 rows (大约)
DELETE FROM `comments`;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;

-- 导出  表 tree.rlats 结构
DROP TABLE IF EXISTS `rlats`;
CREATE TABLE IF NOT EXISTS `rlats` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rid` int NOT NULL DEFAULT '0',
  `pid` int NOT NULL DEFAULT '0',
  `bid` int NOT NULL DEFAULT '0',
  `lft` int NOT NULL DEFAULT '0',
  `rght` int NOT NULL DEFAULT '0',
  `son` int NOT NULL DEFAULT '0',
  `child` int NOT NULL DEFAULT '0',
  `level` int NOT NULL DEFAULT '0',
  `seq` int NOT NULL DEFAULT '0',
  `created` bigint DEFAULT '0' COMMENT '创建时间',
  `updated` bigint DEFAULT '0' COMMENT '修改时间',
  `del_flg` tinyint(1) DEFAULT '0' COMMENT '逻辑标识，0-正常；1-删除；',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='评论表';

-- 正在导出表  tree.rlats 的数据：~0 rows (大约)
DELETE FROM `rlats`;
/*!40000 ALTER TABLE `rlats` DISABLE KEYS */;
/*!40000 ALTER TABLE `rlats` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
