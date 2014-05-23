INSERT INTO `actions`.`element_definition` (`element_definition_id`, `name`, `display_name`, `implementation`, `library_id`, `is_script`, `icon_id`) VALUES ('21', 'FOR_EACH', 'For each router', 'com.abstractions.instance.routing.ForEachRouter', '1', '0', '19');
INSERT INTO `actions`.`element_definition` (`element_definition_id`, `name`, `display_name`, `implementation`, `library_id`, `is_script`, `icon_id`) VALUES ('22', 'FOR_EACH_CONNECTION', 'For each connection', 'com.abstractions.instance.core.ForEachConnection', '1', '0', '9');

INSERT INTO `actions`.`element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) VALUES ('43', '21', 'name', 'Name', 'STRING', 'For each router');
INSERT INTO `actions`.`element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) VALUES ('44', '22', 'name', 'Name', 'STRING', 'New for each connection');

INSERT INTO `actions`.`connection_definition` (`element_definition_id`, `color`, `accepted_source_types`, `accepted_target_types`, `accepted_source_max`, `accepted_target_max`) VALUES ('22', '000000', 'FOR_EACH', '*', '1', '1');
INSERT INTO `actions`.`router_definition` (`element_definition_id`, `router_evaluator_implementation`, `is_router_evaluator_script`) VALUES ('21', 'com.abstractions.runtime.interpreter.ForEachRouterEvaluator', '0');
