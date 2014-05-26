USE `actions`;

DROP TABLE IF EXISTS `connector_definition`;

CREATE  TABLE `actions`.`connector_definition` (
  `element_definition_id` BIGINT NOT NULL ,
  PRIMARY KEY (`element_definition_id`) );

  
ALTER TABLE `actions`.`connector_definition` 
  ADD CONSTRAINT `FK_CONNECTOR_ELEMENT`
  FOREIGN KEY (`element_definition_id` )
  REFERENCES `actions`.`element_definition` (`element_definition_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `FK_CONNECTOR_ELEMENT_idx` (`element_definition_id` ASC) ;


INSERT INTO `actions`.`library` (`library_id`, `name`, `display_name`) VALUES ('4', 'connectors', 'Connectors');
INSERT INTO `actions`.`element_definition` (`element_definition_id`, `name`, `display_name`, `implementation`, `library_id`, `is_script`) VALUES (23, 'SQL_CONNECTOR', 'SQL connector', 'com.modules.sql.SQLConnector', '4', '0');  
INSERT INTO `actions`.`connector_definition` (`element_definition_id`) VALUES ('23');
INSERT INTO `actions`.`element_property_definition` (`element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) VALUES ('14', 'connector', 'Connector', 'STRING', ' ');

