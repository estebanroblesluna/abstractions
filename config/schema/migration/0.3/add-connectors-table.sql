USE `actions`;

DROP TABLE IF EXISTS `connector`;
CREATE TABLE `connector` (
  `connector_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `team_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`connector_id`),
  KEY `fk_connector_1_idx` (`team_id`),
  CONSTRAINT `fk_connector_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`)
);

DROP TABLE IF EXISTS `connector_configurations`;
CREATE TABLE `connector_configurations` (
  `connector_configurations_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `connector_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`connector_configurations_id`),
  KEY `fk_connector_configurations_1_idx` (`connector_id`),
  CONSTRAINT `fk_connector_configurations_1` FOREIGN KEY (`connector_id`) REFERENCES `connector` (`connector_id`) ON DELETE CASCADE ON UPDATE NO ACTION
);


