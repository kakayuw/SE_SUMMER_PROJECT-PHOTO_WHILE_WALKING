CREATE DATABASE bzbp IF NOT EXISTS;
USE bzbp;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	  `uid` int(11) NOT NULL AUTO_INCREMENT,
	  `username` varchar(100) DEFAULT NULL,
	  `password` varchar(100) DEFAULT NULL,
	  `email` varchar(100) DEFAULT NULL,
	  `phone` varchar(100) DEFAULT NULL,
	  PRIMARY KEY (`uid`),
	  UNIQUE KEY `username` (`username`),
	  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
	  `fid` int(11) NOT NULL AUTO_INCREMENT,
	  `uid1` int(11) NOT NULL,
	  `uid2` int(11) NOT NULL,
	  `remark` varchar(100) DEFAULT NULL,
	  `auth` smallint(6) DEFAULT NULL,
	  PRIMARY KEY (`fid`),
	  KEY `FK_Friend` (`uid1`),
	  KEY `FK_Friend2` (`uid2`),
	  CONSTRAINT `FK_Friend` FOREIGN KEY (`uid1`) REFERENCES `user` (`uid`),
	  CONSTRAINT `FK_Friend2` FOREIGN KEY (`uid2`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
	  `aid` int(11) NOT NULL AUTO_INCREMENT,
	  `username` varchar(45) DEFAULT NULL,
	  `password` varchar(45) DEFAULT NULL,
	  PRIMARY KEY (`aid`),
	  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

