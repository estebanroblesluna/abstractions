USE `actions`;

INSERT INTO `element_definition` (`element_definition_id`, `name`, `display_name`, `icon`, `implementation`, `library_id`, `is_script`) VALUES (20,'RESOURCE_DUST_RENDERER','Resource based Dust renderer','Resources/groovy.gif','com.modules.dust.ResourceBasedDustRendererProcessor',2,0);
INSERT INTO `processor_definition` (`element_definition_id`) VALUES (20);

INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) VALUES (38,20,'templateList','Template list','STRING','string');
INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) VALUES (39,20,'jsonData','JSON data expression','EXPRESSION','exp');