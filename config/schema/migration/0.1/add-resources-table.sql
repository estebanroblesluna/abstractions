USE `actions`;

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
	`resource_id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`path` VARCHAR(256) NOT NULL,
	`data` blob NOT NULL,
	`application_id` INT(20) UNSIGNED NOT NULL,
	`last_modified_date` DATE NOT NULL,
	`type` VARCHAR(32) NOT NULL,
	PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
