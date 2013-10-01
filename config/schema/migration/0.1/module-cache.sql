INSERT INTO `element_definition` (`element_definition_id`, `name`, `display_name`, `icon`, `implementation`, `library_id`, `is_script`) 
VALUES (15,'GET_MEMCACHED','Get Memcached','Resources/memcached-get.png','com.modules.cache.GetCacheProcessor', 2, 0);
INSERT INTO `processor_definition` (`element_definition_id`) VALUES (15);

INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) 
VALUES (31, 15,'name','Name','STRING','New get memcached');
INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) 
VALUES (32, 15,'expression','GET expression','EXPRESSION','exp');


INSERT INTO `element_definition` (`element_definition_id`, `name`, `display_name`, `icon`, `implementation`, `library_id`, `is_script`) 
VALUES (16,'PUT_MEMCACHED','Put Memcached','Resources/memcached-put.png','com.modules.cache.PutCacheProcessor', 2, 0);
INSERT INTO `processor_definition` (`element_definition_id`) VALUES (16);

INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) 
VALUES (33, 16,'name','Name','STRING','New put memcached');
INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) 
VALUES (34, 16,'keyExpression','KEY expression','EXPRESSION','exp');
INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) 
VALUES (35, 16,'valueExpression','VALUE expression','EXPRESSION','exp');
