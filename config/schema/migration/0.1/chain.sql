INSERT INTO `element_definition` (`element_definition_id`, `name`, `display_name`, `icon`, `implementation`, `library_id`, `is_script`) 
VALUES (17,'CHAIN','Chain router','Resources/chain.gif','com.core.routing.ChainRouter',1,0);
INSERT INTO `router_definition` (`element_definition_id`, `router_evaluator_implementation`, `is_router_evaluator_script`) 
VALUES (17,'com.core.interpreter.ChainRouterEvaluator',0);
INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) 
VALUES (36,17,'name','Name','STRING','New chain');


INSERT INTO `element_definition` (`element_definition_id`, `name`, `display_name`, `icon`, `implementation`, `library_id`, `is_script`) 
VALUES (18,'CHAIN_CONNECTION','Chain connection','Resources/chainConnection.png','com.core.impl.ChainConnection',1,0);
INSERT INTO `connection_definition` (`element_definition_id`, `color`, `accepted_source_types`, `accepted_target_types`, `accepted_source_max`, `accepted_target_max`) 
VALUES (18,'ABCABC','CHAIN','*',1,2147483647);
INSERT INTO `element_property_definition` (`property_definition_id`, `element_definition_id`, `name`, `display_name`, `property_type`, `default_value`) 
VALUES (37,18,'name','Name','STRING','New chain connection');
