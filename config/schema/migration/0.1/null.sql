USE `actions`;

INSERT INTO `element_definition` (`element_definition_id`, `name`, `display_name`, `icon`, `implementation`, `library_id`, `is_script`) 
VALUES (19,'NULL','Null processor','Resources/groovy.gif','com.core.common.NullProcessor',1,0);
INSERT INTO `processor_definition` (`element_definition_id`) VALUES (19);
