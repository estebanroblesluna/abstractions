USE `actions`;

INSERT INTO `element_definition` (`element_definition_id`, `name`, `display_name`, `icon`, `implementation`, `library_id`, `is_script`) 
VALUES (14,'SQL_SELECT','SQL Select','Resources/selectSql.png','com.modules.sql.SelectProcessor', 2, 0);

INSERT INTO `processor_definition` (`element_definition_id`) VALUES (14);

INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) 
VALUES (28,14,'name','Name','STRING','New sql selected');
INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) 
VALUES (29,14,'selectExpression','SQL select expression','EXPRESSION','exp');
INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) 
VALUES (30,14,'parameterExpressions','Parameters','STRING','');

