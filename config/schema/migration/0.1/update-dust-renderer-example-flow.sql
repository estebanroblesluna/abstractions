USE `actions`;

DELETE FROM `flows` WHERE flow_id = 23;

INSERT INTO `flows` VALUES (23,'1ce8b357-9d82-4fdd-8c39-ac346b059bc6','{\"id\":\"1ce8b357-9d82-4fdd-8c39-ac346b059bc6\",\"version\":0,\"definitions\":[{\"id\":\"99329d60-329a-461f-adf9-6b7367436a58\",\"name\":\"GROOVY\",\"properties\":{\"__incoming_connections\":\"list(urn:795fc50e-c91e-468e-a3a0-428af5354663)\",\"__next_in_chain\":\"urn:6043c76f-bde3-4178-a7f7-d03ef6ad5c93\",\"__connections\":\"list(urn:6043c76f-bde3-4178-a7f7-d03ef6ad5c93)\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:6043c76f-bde3-4178-a7f7-d03ef6ad5c93)\",\"name\":\"Profile info\",\"x\":\"381\",\"y\":\"70\",\"script\":\"message.payload = \'{\\\"firstName\\\":\\\"Gustavo\\\",\\\"lastName\\\":\\\"Rossi\\\",\\\"position\\\":\\\"Investigador\\\",\\\"address\\\":\\\"Lifia, calle 50 y 115, La Plata, Prov. Buenos Aires, Argentina\\\",\\\"phone\\\":\\\"(+54 221) 423 6585 ext 213\\\",\\\"fax\\\":\\\"(+54 221) 422 8252\\\"}\'\",\"_breakpoint\":false}},{\"id\":\"12170038-2cef-41ea-bce9-02b485ad1cf1\",\"name\":\"RESOURCE_DUST_RENDERER\",\"properties\":{\"__incoming_connections\":\"list(urn:6043c76f-bde3-4178-a7f7-d03ef6ad5c93)\",\"templateRenderingList\":\"(templates\\/t.tl,main,message.payload)\",\"resourcesList\":\"lib\\/dust.js;lib\\/jquery-2.0.3.min.js\",\"name\":\"Renderer\",\"x\":\"537\",\"y\":\"37\",\"bodyTemplatePath\":\"body.tl\",\"_breakpoint\":false}},{\"id\":\"1f8e6bc9-6b34-43bd-9770-f8320f3d68bd\",\"name\":\"HTTP_MESSAGE_SOURCE\",\"properties\":{\"__next_in_chain\":\"urn:795fc50e-c91e-468e-a3a0-428af5354663\",\"__connections\":\"list(urn:795fc50e-c91e-468e-a3a0-428af5354663)\",\"port\":\"8822\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:795fc50e-c91e-468e-a3a0-428af5354663)\",\"name\":\"Listener\",\"x\":\"236\",\"y\":\"54\",\"_breakpoint\":false}},{\"id\":\"795fc50e-c91e-468e-a3a0-428af5354663\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"303,68;381,76;\",\"source\":\"urn:1f8e6bc9-6b34-43bd-9770-f8320f3d68bd\",\"target\":\"urn:99329d60-329a-461f-adf9-6b7367436a58\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\",\"_breakpoint\":false}},{\"id\":\"6043c76f-bde3-4178-a7f7-d03ef6ad5c93\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"462,71;537,55;\",\"source\":\"urn:99329d60-329a-461f-adf9-6b7367436a58\",\"target\":\"urn:12170038-2cef-41ea-bce9-02b485ad1cf1\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\",\"_breakpoint\":false}}]}',2,NULL);

