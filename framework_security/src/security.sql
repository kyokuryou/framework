DROP TABLE IF EXISTS `sec_admin`;
CREATE TABLE `sec_admin` (
  `pk_id` varchar(32) NOT NULL,
  `create_date` datetime default NULL,
  `modify_date` datetime default NULL,
  `is_enabled` bit(1) NOT NULL,
  `is_expired` bit(1) NOT NULL,
  `is_locked` bit(1) NOT NULL,
  `is_credentials_expired` bit(1) NOT NULL,
  `locked_date` datetime DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `login_failure_count` int(11) NOT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY  (`pk_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sec_role`;
CREATE TABLE `sec_role` (
  `pk_id` varchar(32) NOT NULL,
  `create_date` datetime default NULL,
  `modify_date` datetime default NULL,
  `description` text,
  `is_system` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY  (`pk_id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `value` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sec_admin_role`;
CREATE TABLE `sec_admin_role` (
  `pk_id` varchar(32) NOT NULL,
  `create_date` datetime default NULL,
  `modify_date` datetime default NULL,
  `admin_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  PRIMARY KEY  (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sec_resource`;
CREATE TABLE `sec_resource` (
  `pk_id` varchar(32) NOT NULL,
  `create_date` datetime default NULL,
  `modify_date` datetime default NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY  (`pk_id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `value` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sec_role_resource`;
CREATE TABLE `sec_role_resource` (
  `pk_id` varchar(32) NOT NULL,
  `create_date` datetime default NULL,
  `modify_date` datetime default NULL,
  `role_id` varchar(32) NOT NULL,
  `resource_id` varchar(32) NOT NULL,
  PRIMARY KEY  (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
