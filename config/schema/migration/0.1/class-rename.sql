USE `actions`;

UPDATE `actions`.`router_definition` SET `router_evaluator_implementation`='com.abstractions.runtime.interpreter.ChoiceRouterEvaluator' WHERE `element_definition_id`='3';
UPDATE `actions`.`router_definition` SET `router_evaluator_implementation`='com.abstractions.runtime.interpreter.AllRouterEvaluator' WHERE `element_definition_id`='5';
UPDATE `actions`.`router_definition` SET `router_evaluator_implementation`='com.abstractions.runtime.interpreter.WireTapRouterEvaluator' WHERE `element_definition_id`='7';
UPDATE `actions`.`router_definition` SET `router_evaluator_implementation`='com.abstractions.runtime.interpreter.ChainRouterEvaluator' WHERE `element_definition_id`='17';

UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.common.ScriptingProcessor' WHERE `element_definition_id`='1';
UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.core.NextInChainConnection' WHERE `element_definition_id`='2';
UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.core.ChoiceConnection' WHERE `element_definition_id`='4';
UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.core.AllConnection' WHERE `element_definition_id`='6';
UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.core.WireTapConnection' WHERE `element_definition_id`='8';
UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.routing.ChoiceRouter' WHERE `element_definition_id`='3';
UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.routing.AllRouter' WHERE `element_definition_id`='5';
UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.routing.WireTapRouter' WHERE `element_definition_id`='7';
UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.common.LogProcessor' WHERE `element_definition_id`='13';
UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.routing.ChainRouter' WHERE `element_definition_id`='17';
UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.core.ChainConnection' WHERE `element_definition_id`='18';
UPDATE `actions`.`element_definition` SET `implementation`='com.abstractions.instance.common.NullProcessor' WHERE `element_definition_id`='19';
