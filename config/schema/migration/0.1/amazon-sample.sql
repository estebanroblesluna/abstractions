USE `actions`;

INSERT INTO `application`(`application_id`, `name`, `team_id`) VALUES(3, 'Amazon', 1);


INSERT INTO `flows` (`flow_id`, `name`, `json_representation`, `application_id`, `application_snapshot_id`) VALUES (6,'c7940ddb-497e-4b14-98e7-67bdf953ed9b','{\"id\":\"c7940ddb-497e-4b14-98e7-67bdf953ed9b\",\"version\":1,\"definitions\":[{\"id\":\"b86628ad-0ac6-4cf2-a196-98deb940ac53\",\"name\":\"CHOICE\",\"properties\":{\"__connectionsCHOICE_CONNECTION\":\"list(urn:051a488b-7eeb-491c-9749-9f2f49f7c7c7,urn:67491897-c03a-43d4-97eb-c1d46de9b85a)\",\"name\":\"Path router\",\"_breakpoint\":false,\"x\":\"213\",\"y\":\"239\"}},{\"id\":\"1321cec6-204d-4359-bb79-aed246ad3b4d\",\"name\":\"SQL_SELECT\",\"properties\":{\"__next_in_chain\":\"urn:0c9c4207-86bb-40b8-89e3-a8c1f87c6aca\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:0c9c4207-86bb-40b8-89e3-a8c1f87c6aca)\",\"name\":\"Get product info\",\"selectExpression\":\"\'SELECT name, price FROM amazon.product where product_oid = ?\'\",\"_breakpoint\":false,\"x\":\"718\",\"y\":\"357\",\"parameterExpressions\":\"message.properties[\'actions.http.productId\']\"}},{\"id\":\"e6d00e52-5e31-4e96-a266-7fc734386049\",\"name\":\"DUST_RENDERER\",\"properties\":{\"template\":\"\'Product: {name} <br> Price: {price} <br> Rank: {rank}\'\",\"name\":\"Render template\",\"_breakpoint\":false,\"jsonData\":\"message.payload\",\"x\":\"195\",\"y\":\"179\"}},{\"id\":\"20a2dad7-e559-45ec-9f39-957aaabd8288\",\"name\":\"GROOVY\",\"properties\":{\"__next_in_chain\":\"urn:48891666-b9a3-4ea2-970d-c25a7c6f007c\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:48891666-b9a3-4ea2-970d-c25a7c6f007c)\",\"name\":\"N1\",\"_breakpoint\":false,\"x\":\"644\",\"y\":\"155\"}},{\"id\":\"e45a6daf-f25d-4e66-84cb-a2ca10317687\",\"name\":\"GROOVY\",\"properties\":{\"name\":\"Save product info\",\"_breakpoint\":false,\"x\":\"890\",\"y\":\"357\",\"script\":\"message.properties[\'productInfo\'] = message.payload\"}},{\"id\":\"f2000017-d7cc-47cc-a49b-a24f4760baa3\",\"name\":\"GROOVY\",\"properties\":{\"__next_in_chain\":\"urn:b5bab5ab-72e7-40f7-9eca-9454d68030c9\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:5007efd0-02e1-47bd-baea-0fedca999dfe,urn:b5bab5ab-72e7-40f7-9eca-9454d68030c9)\",\"name\":\"Compose data\",\"_breakpoint\":false,\"x\":\"350\",\"y\":\"179\",\"script\":\"message.payload = \'{\\\"name\\\":\\\"\' + message.properties[\'productInfo\'][0][0] + \'\\\",\\\"price\\\":\\\"\' + message.properties[\'productInfo\'][0][1] + \'\\\",\\\"rank\\\":\\\"\' + message.properties[\'rank\'] + \'\\\"}\'\"}},{\"id\":\"cf644c2c-83ca-4248-b7d7-83e83bec8a55\",\"name\":\"GROOVY\",\"properties\":{\"name\":\"Save rank\",\"_breakpoint\":false,\"x\":\"890\",\"y\":\"286\",\"script\":\"message.properties[\'rank\'] = message.payload\"}},{\"id\":\"cdd2a9b1-ef6c-43ba-b218-83c1b9bc7962\",\"name\":\"GROOVY\",\"properties\":{\"__next_in_chain\":\"urn:f8d902f6-0bf4-4374-a3f2-330b02770b07\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:f8d902f6-0bf4-4374-a3f2-330b02770b07)\",\"name\":\"N4\",\"x\":\"644\",\"y\":\"357\",\"_breakpoint\":false}},{\"id\":\"74f3996a-c510-478d-b434-abb94fecd935\",\"name\":\"GROOVY\",\"properties\":{\"name\":\"Save reputation\",\"_breakpoint\":false,\"x\":\"890\",\"y\":\"155\",\"script\":\"message.properties[\'reputation\'] = message.payload\"}},{\"id\":\"321515ed-2667-4077-a90f-13730e23d839\",\"name\":\"CHAIN\",\"properties\":{\"name\":\"Get product rank\",\"_breakpoint\":false,\"x\":\"508\",\"y\":\"210\",\"__connectionsCHAIN_CONNECTION\":\"list(urn:95f0cae7-16cb-4831-a919-c61e66ca606c,urn:d6881d7a-1471-4b3a-9066-602b5b0005ca,urn:74206aaa-46b5-4d1d-86c5-2a422fed6f81)\"}},{\"id\":\"8fecec88-2764-4bd5-b20a-60b545110ce2\",\"name\":\"GROOVY\",\"properties\":{\"__next_in_chain\":\"urn:61f6d779-99a0-4ef1-bfd6-6172d28d50d6\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:61f6d779-99a0-4ef1-bfd6-6172d28d50d6)\",\"name\":\"N2\",\"_breakpoint\":false,\"x\":\"644\",\"y\":\"212\"}},{\"id\":\"9894b25c-ec59-4a05-b129-233ccc51f186\",\"name\":\"GROOVY\",\"properties\":{\"__next_in_chain\":\"urn:9f98d29c-ca75-4994-9b01-6dcb1a6774fa\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:c951d90b-6900-4794-bf4a-a2216936053f,urn:9f98d29c-ca75-4994-9b01-6dcb1a6774fa)\",\"name\":\"Compute product rank\",\"_breakpoint\":false,\"x\":\"718\",\"y\":\"286\",\"script\":\"def reputationMapping = []; def totalReputation = 0; def userReputationResult = message.properties[\'reputation\'];  for (row in userReputationResult) { \\treputationMapping[row[0]] = row[1]; \\ttotalReputation = row[1]; };  def computedRank = 0; def reviewResult = message.properties[\'reviews\']; \\t  for (row in reviewResult) { \\tif (reputationMapping[row[0]] != null) { \\t\\tdef reputation = reputationMapping[row[0]]; \\t\\tcomputedRank = computedRank + (reputation * row[1]); \\t}; }; message.payload = computedRank;\"}},{\"id\":\"da7a0085-4809-499b-851f-33b0acce5364\",\"name\":\"GROOVY\",\"properties\":{\"name\":\"Save product reviews\",\"_breakpoint\":false,\"x\":\"890\",\"y\":\"212\",\"script\":\"message.properties[\'reviews\'] = message.payload\"}},{\"id\":\"c8ea1ead-9af4-4131-b980-02ece85fc08a\",\"name\":\"SQL_SELECT\",\"properties\":{\"__next_in_chain\":\"urn:af7a076c-d54c-43f2-96fb-026ad2dfe5c3\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:af7a076c-d54c-43f2-96fb-026ad2dfe5c3)\",\"name\":\"Get product reviews\",\"selectExpression\":\"\'SELECT review.user_oid, review.value FROM review WHERE review.product_product_oid = ?\'\",\"_breakpoint\":false,\"x\":\"718\",\"y\":\"212\",\"parameterExpressions\":\"message.properties[\'actions.http.productId\']\"}},{\"id\":\"b89218fd-7c54-4c2d-b1ed-c06adae979b6\",\"name\":\"GROOVY\",\"properties\":{\"__next_in_chain\":\"urn:49caafc9-2188-4931-af4e-5e22a59f3629\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:49caafc9-2188-4931-af4e-5e22a59f3629)\",\"name\":\"N3\",\"_breakpoint\":false,\"x\":\"644\",\"y\":\"286\"}},{\"id\":\"6482f58e-3e67-4414-8f73-b059dd25f6d5\",\"name\":\"HTTP_MESSAGE_SOURCE\",\"properties\":{\"__next_in_chain\":\"urn:ff8f2778-103c-45dc-834a-3464b68d2dcb\",\"port\":\"8822\",\"timeoutExpression\":\"-1l\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:ff8f2778-103c-45dc-834a-3464b68d2dcb)\",\"name\":\"HTTP listener\",\"_breakpoint\":false,\"x\":\"32\",\"y\":\"239\"}},{\"id\":\"08de4bd1-c3db-49fc-a1ff-ae1509da1170\",\"name\":\"CHAIN\",\"properties\":{\"__next_in_chain\":\"urn:b0ecf623-f5e8-4b84-84f3-cd473a73b071\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:b0ecf623-f5e8-4b84-84f3-cd473a73b071)\",\"name\":\"Get info\",\"_breakpoint\":false,\"x\":\"392\",\"y\":\"272\",\"__connectionsCHAIN_CONNECTION\":\"list(urn:24591fa8-d5ae-4fd4-a906-b33fe1707cdb,urn:67bee960-66f7-4bcb-b29a-488b7f0d768b,urn:f323f9b2-dec2-4a28-8fda-b337d632d66f)\"}},{\"id\":\"ac39acb7-ff73-425d-87c8-0908c796e0ee\",\"name\":\"SQL_SELECT\",\"properties\":{\"__next_in_chain\":\"urn:2c16770a-7bbb-4615-82b7-ebb644731018\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:2c16770a-7bbb-4615-82b7-ebb644731018)\",\"name\":\"Get user reputation\",\"selectExpression\":\"\'SELECT review.user_oid, sum(valuation.value * (365 \\/ (DATEDIFF(NOW(), valuation.valuation_date) + 1))) as user_valuation FROM review inner join valuation on (review.review_oid = valuation.review_review_oid) WHERE review.user_oid IN (select review.user_oid from review where product_product_oid = ?) GROUP BY review.user_oid\'\",\"_breakpoint\":false,\"x\":\"718\",\"y\":\"155\",\"parameterExpressions\":\"message.properties[\'actions.http.productId\']\"}},{\"id\":\"9f98d29c-ca75-4994-9b01-6dcb1a6774fa\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"865,297;890,297;\",\"source\":\"urn:9894b25c-ec59-4a05-b129-233ccc51f186\",\"_breakpoint\":false,\"target\":\"urn:cf644c2c-83ca-4248-b7d7-83e83bec8a55\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"67491897-c03a-43d4-97eb-c1d46de9b85a\",\"name\":\"CHOICE_CONNECTION\",\"properties\":{\"points\":\"299,258;392,276;\",\"expression\":\"message.properties[\'actions.http.requestURI\'].startsWith(\'\\/product\\/\')\",\"source\":\"urn:b86628ad-0ac6-4cf2-a196-98deb940ac53\",\"_breakpoint\":false,\"target\":\"urn:08de4bd1-c3db-49fc-a1ff-ae1509da1170\",\"type\":\"CHOICE_CONNECTION\"}},{\"id\":\"b0ecf623-f5e8-4b84-84f3-cd473a73b071\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"423,272;406,201;\",\"source\":\"urn:08de4bd1-c3db-49fc-a1ff-ae1509da1170\",\"_breakpoint\":false,\"target\":\"urn:f2000017-d7cc-47cc-a49b-a24f4760baa3\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"af7a076c-d54c-43f2-96fb-026ad2dfe5c3\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"852,223;890,223;\",\"source\":\"urn:c8ea1ead-9af4-4131-b980-02ece85fc08a\",\"_breakpoint\":false,\"target\":\"urn:da7a0085-4809-499b-851f-33b0acce5364\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"95f0cae7-16cb-4831-a919-c61e66ca606c\",\"name\":\"CHAIN_CONNECTION\",\"properties\":{\"points\":\"586,210;645,177;\",\"source\":\"urn:321515ed-2667-4077-a90f-13730e23d839\",\"_breakpoint\":false,\"target\":\"urn:20a2dad7-e559-45ec-9f39-957aaabd8288\",\"type\":\"CHAIN_CONNECTION\"}},{\"id\":\"f8d902f6-0bf4-4374-a3f2-330b02770b07\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"686,368;718,368;\",\"source\":\"urn:cdd2a9b1-ef6c-43ba-b218-83c1b9bc7962\",\"target\":\"urn:1321cec6-204d-4359-bb79-aed246ad3b4d\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\",\"_breakpoint\":false}},{\"id\":\"b5bab5ab-72e7-40f7-9eca-9454d68030c9\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"350,190;312,190;\",\"source\":\"urn:f2000017-d7cc-47cc-a49b-a24f4760baa3\",\"_breakpoint\":false,\"target\":\"urn:e6d00e52-5e31-4e96-a266-7fc734386049\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"0c9c4207-86bb-40b8-89e3-a8c1f87c6aca\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"830,368;890,368;\",\"source\":\"urn:1321cec6-204d-4359-bb79-aed246ad3b4d\",\"_breakpoint\":false,\"target\":\"urn:e45a6daf-f25d-4e66-84cb-a2ca10317687\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"49caafc9-2188-4931-af4e-5e22a59f3629\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"686,297;718,297;\",\"source\":\"urn:b89218fd-7c54-4c2d-b1ed-c06adae979b6\",\"_breakpoint\":false,\"target\":\"urn:9894b25c-ec59-4a05-b129-233ccc51f186\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"24591fa8-d5ae-4fd4-a906-b33fe1707cdb\",\"name\":\"CHAIN_CONNECTION\",\"properties\":{\"points\":\"451,272;541,232;\",\"source\":\"urn:08de4bd1-c3db-49fc-a1ff-ae1509da1170\",\"_breakpoint\":false,\"target\":\"urn:321515ed-2667-4077-a90f-13730e23d839\",\"type\":\"CHAIN_CONNECTION\"}},{\"id\":\"48891666-b9a3-4ea2-970d-c25a7c6f007c\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"686,166;718,166;\",\"source\":\"urn:20a2dad7-e559-45ec-9f39-957aaabd8288\",\"_breakpoint\":false,\"target\":\"urn:ac39acb7-ff73-425d-87c8-0908c796e0ee\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"ff8f2778-103c-45dc-834a-3464b68d2dcb\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"132,250;213,250;\",\"source\":\"urn:6482f58e-3e67-4414-8f73-b059dd25f6d5\",\"_breakpoint\":false,\"target\":\"urn:b86628ad-0ac6-4cf2-a196-98deb940ac53\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"61f6d779-99a0-4ef1-bfd6-6172d28d50d6\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"686,223;718,223;\",\"source\":\"urn:8fecec88-2764-4bd5-b20a-60b545110ce2\",\"_breakpoint\":false,\"target\":\"urn:c8ea1ead-9af4-4131-b980-02ece85fc08a\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"d6881d7a-1471-4b3a-9066-602b5b0005ca\",\"name\":\"CHAIN_CONNECTION\",\"properties\":{\"points\":\"624,222;644,223;\",\"source\":\"urn:321515ed-2667-4077-a90f-13730e23d839\",\"_breakpoint\":false,\"target\":\"urn:8fecec88-2764-4bd5-b20a-60b545110ce2\",\"type\":\"CHAIN_CONNECTION\"}},{\"id\":\"74206aaa-46b5-4d1d-86c5-2a422fed6f81\",\"name\":\"CHAIN_CONNECTION\",\"properties\":{\"points\":\"580,232;651,286;\",\"source\":\"urn:321515ed-2667-4077-a90f-13730e23d839\",\"_breakpoint\":false,\"target\":\"urn:b89218fd-7c54-4c2d-b1ed-c06adae979b6\",\"type\":\"CHAIN_CONNECTION\"}},{\"id\":\"f323f9b2-dec2-4a28-8fda-b337d632d66f\",\"name\":\"CHAIN_CONNECTION\",\"properties\":{\"points\":\"457,294;644,361;\",\"source\":\"urn:08de4bd1-c3db-49fc-a1ff-ae1509da1170\",\"target\":\"urn:cdd2a9b1-ef6c-43ba-b218-83c1b9bc7962\",\"type\":\"CHAIN_CONNECTION\",\"_breakpoint\":false}},{\"id\":\"2c16770a-7bbb-4615-82b7-ebb644731018\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"848,166;890,166;\",\"source\":\"urn:ac39acb7-ff73-425d-87c8-0908c796e0ee\",\"_breakpoint\":false,\"target\":\"urn:74f3996a-c510-478d-b434-abb94fecd935\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}}]}',3,NULL);
INSERT INTO `flows` (`flow_id`, `name`, `json_representation`, `application_id`, `application_snapshot_id`) VALUES (11,'c7940ddb-497e-4b14-98e7-67bdf953ed9c','{\"id\":\"c7940ddb-497e-4b14-98e7-67bdf953ed9c\",\"version\":1,\"definitions\":[{\"id\":\"b86628ad-0ac6-4cf2-a196-98deb940ac53\",\"name\":\"CHOICE\",\"properties\":{\"__connectionsCHOICE_CONNECTION\":\"list(urn:051a488b-7eeb-491c-9749-9f2f49f7c7c7)\",\"name\":\"Path router\",\"_breakpoint\":false,\"x\":\"186\",\"y\":\"239\"}},{\"id\":\"1321cec6-204d-4359-bb79-aed246ad3b4d\",\"name\":\"SQL_SELECT\",\"properties\":{\"name\":\"Get product info\",\"selectExpression\":\"\'SELECT name, price FROM amazon.product where product_oid = ?\'\",\"_breakpoint\":false,\"x\":\"181\",\"y\":\"310\",\"parameterExpressions\":\"message.properties[\'actions.http.productId\']\"}},{\"id\":\"3a901bed-4674-485d-a4be-9018d6a73612\",\"name\":\"WIRE_TAP\",\"properties\":{\"__connectionsWIRE_TAP_CONNECTION\":\"list(urn:195fb13c-9379-4781-8448-ff50f715e222)\",\"name\":\"Async cache\",\"_breakpoint\":false,\"x\":\"567\",\"y\":\"310\"}},{\"id\":\"e6d00e52-5e31-4e96-a266-7fc734386049\",\"name\":\"DUST_RENDERER\",\"properties\":{\"template\":\"\'Product: {name} <br> Price: {price} <br> Rank: {rank}\'\",\"name\":\"Render template\",\"_breakpoint\":false,\"jsonData\":\"message.payload\",\"x\":\"115\",\"y\":\"179\"}},{\"id\":\"cc80ea9b-da86-4377-85ce-7a59abccbdf9\",\"name\":\"ALL\",\"properties\":{\"__next_in_chain\":\"urn:b8a03675-a10c-41db-bcfe-badee3d66396\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:b8a03675-a10c-41db-bcfe-badee3d66396)\",\"name\":\"Get product rank\",\"_breakpoint\":false,\"x\":\"772\",\"y\":\"239\",\"__connectionsALL_CONNECTION\":\"list(urn:4cc5a122-c95e-409a-8cb2-f99f712b66bd,urn:099dedee-f7d3-4758-af6d-04ea3dfbd82b)\"}},{\"id\":\"0e1f3c7f-dacd-4415-b122-39e5a54601e1\",\"name\":\"ALL\",\"properties\":{\"__next_in_chain\":\"urn:df9a2410-be36-4206-a014-5e6a2a1dac02\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:ee6f5a13-bd22-465c-bc61-5ed3d0fd306b,urn:40ab8865-d169-4616-bb71-d70013beaeaf,urn:df9a2410-be36-4206-a014-5e6a2a1dac02)\",\"name\":\"Get info\",\"_breakpoint\":false,\"x\":\"311\",\"y\":\"239\",\"__connectionsALL_CONNECTION\":\"list(urn:81c05ce5-f6a6-4ee2-9035-f9c3a751c092,urn:c47939d2-3faa-4bff-a6e7-744484bf30f1,urn:30e28538-fa52-4a45-b2db-d6737bc2e544,urn:bf9cab2b-778f-4462-b3c1-9f886b8c897d,urn:6412eea3-99e9-4e0a-8d2c-be9bd2008c2a,urn:3e367679-4b6d-4c1b-a989-b5b4e2a629e8,urn:55944e08-1004-421c-b613-d9d6919c6174,urn:1f65e8df-7761-4c4b-8658-b9f777281a3c,urn:14f5321b-ffad-43ae-a592-a2d45b249b7a)\"}},{\"id\":\"1e15de73-e6ac-4ea1-82d6-e5251fafa994\",\"name\":\"CHAIN\",\"properties\":{\"name\":\"Chain\",\"_breakpoint\":false,\"x\":\"672\",\"y\":\"239\",\"__connectionsCHAIN_CONNECTION\":\"list(urn:8c35f37f-9d6c-46c9-8fcc-3235c8846eaa,urn:d693ed94-a7b5-46a4-985a-ad50dd9a0197,urn:dd563e1a-d9e5-4c7c-8687-6e53909c2678)\"}},{\"id\":\"055cfbce-3ca4-4553-8a47-a6cf347eec2c\",\"name\":\"GET_MEMCACHED\",\"properties\":{\"__next_in_chain\":\"urn:dca9d407-8e17-4da6-a7ae-81327cb48fd4\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:dca9d407-8e17-4da6-a7ae-81327cb48fd4)\",\"name\":\"Get cached value\",\"_breakpoint\":false,\"x\":\"411\",\"y\":\"239\"}},{\"id\":\"f2000017-d7cc-47cc-a49b-a24f4760baa3\",\"name\":\"GROOVY\",\"properties\":{\"__next_in_chain\":\"urn:b5bab5ab-72e7-40f7-9eca-9454d68030c9\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:5007efd0-02e1-47bd-baea-0fedca999dfe,urn:b5bab5ab-72e7-40f7-9eca-9454d68030c9)\",\"name\":\"Compose data\",\"_breakpoint\":false,\"x\":\"263\",\"y\":\"179\",\"script\":\"message.payload = \'{\\\"name\\\":\\\"\' + message.properties[\'productInfo\'][0][0] + \'\\\",\\\"price\\\":\\\"\' + message.properties[\'productInfo\'][0][1] + \'\\\",\\\"rank\\\":\\\"\' + message.properties[\'rank\'] + \'\\\"}\'\"}},{\"id\":\"81203195-e11c-4741-b93f-bc14ed5e5368\",\"name\":\"CHOICE\",\"properties\":{\"__connectionsCHOICE_CONNECTION\":\"list(urn:df18d708-543d-498c-9393-53ab6f96ac52,urn:abe4d086-db9e-49ca-8e2e-046b2ba41af7)\",\"name\":\"Is cached?\",\"_breakpoint\":false,\"x\":\"553\",\"y\":\"239\"}},{\"id\":\"9894b25c-ec59-4a05-b129-233ccc51f186\",\"name\":\"GROOVY\",\"properties\":{\"__next_in_chain\":\"urn:c951d90b-6900-4794-bf4a-a2216936053f\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:c951d90b-6900-4794-bf4a-a2216936053f)\",\"name\":\"Compute product rank\",\"_breakpoint\":false,\"x\":\"939\",\"y\":\"239\",\"script\":\"def reputationMapping = []; def totalReputation = 0; def userReputationResult = message.properties[\'reputation\'];  for (row in userReputationResult) { \\treputationMapping[row[0]] = row[1]; \\ttotalReputation = row[1]; };  def computedRank = 0; def reviewResult = message.properties[\'reviews\']; \\t  for (row in reviewResult) { \\tif (reputationMapping[row[0]] != null) { \\t\\tdef reputation = reputationMapping[row[0]]; \\t\\tcomputedRank = computedRank + (reputation * row[1]); \\t}; }; message.payload = computedRank;\"}},{\"id\":\"774222a0-06d4-4e49-adbc-20c81a9fc7c4\",\"name\":\"PUT_MEMCACHED\",\"properties\":{\"__next_in_chain\":\"urn:0c884692-3ce4-47e3-b0c3-3d3f4c250828\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:0c884692-3ce4-47e3-b0c3-3d3f4c250828)\",\"name\":\"Put cached value\",\"_breakpoint\":false,\"x\":\"689\",\"y\":\"310\"}},{\"id\":\"db2e8a5d-8649-488c-b693-dbe36f648a24\",\"name\":\"PUT_MEMCACHED\",\"properties\":{\"name\":\"Put last update\",\"_breakpoint\":false,\"x\":\"862\",\"y\":\"310\"}},{\"id\":\"c8ea1ead-9af4-4131-b980-02ece85fc08a\",\"name\":\"SQL_SELECT\",\"properties\":{\"name\":\"Get product reviews\",\"selectExpression\":\"\'SELECT review.user_oid, review.value FROM review WHERE review.product_product_oid = ?\'\",\"_breakpoint\":false,\"x\":\"907\",\"y\":\"277\",\"parameterExpressions\":\"message.properties[\'actions.http.productId\']\"}},{\"id\":\"6482f58e-3e67-4414-8f73-b059dd25f6d5\",\"name\":\"HTTP_MESSAGE_SOURCE\",\"properties\":{\"__next_in_chain\":\"urn:ff8f2778-103c-45dc-834a-3464b68d2dcb\",\"port\":\"8822\",\"timeoutExpression\":\"-1l\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:ff8f2778-103c-45dc-834a-3464b68d2dcb)\",\"name\":\"HTTP listener\",\"_breakpoint\":false,\"x\":\"32\",\"y\":\"239\"}},{\"id\":\"ac39acb7-ff73-425d-87c8-0908c796e0ee\",\"name\":\"SQL_SELECT\",\"properties\":{\"name\":\"Get user reputation\",\"selectExpression\":\"\'SELECT review.user_oid, sum(valuation.value * (365 \\/ (DATEDIFF(NOW(), valuation.valuation_date) + 1))) as user_valuation FROM review inner join valuation on (review.review_oid = valuation.review_review_oid) WHERE review.user_oid IN (select review.user_oid from review where product_product_oid = ?) GROUP BY review.user_oid\'\",\"_breakpoint\":false,\"x\":\"907\",\"y\":\"197\",\"parameterExpressions\":\"message.properties[\'actions.http.productId\']\"}},{\"id\":\"df9a2410-be36-4206-a014-5e6a2a1dac02\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"340,239;321,201;\",\"source\":\"urn:0e1f3c7f-dacd-4415-b122-39e5a54601e1\",\"_breakpoint\":false,\"target\":\"urn:f2000017-d7cc-47cc-a49b-a24f4760baa3\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"051a488b-7eeb-491c-9749-9f2f49f7c7c7\",\"name\":\"CHOICE_CONNECTION\",\"properties\":{\"points\":\"272,250;311,250;\",\"expression\":\"message.properties[\'actions.http.requestURI\'].startsWith(\'\\/product\\/\')\",\"source\":\"urn:b86628ad-0ac6-4cf2-a196-98deb940ac53\",\"_breakpoint\":false,\"target\":\"urn:0e1f3c7f-dacd-4415-b122-39e5a54601e1\",\"type\":\"CHOICE_CONNECTION\"}},{\"id\":\"099dedee-f7d3-4758-af6d-04ea3dfbd82b\",\"name\":\"ALL_CONNECTION\",\"properties\":{\"points\":\"872,261;932,277;\",\"source\":\"urn:cc80ea9b-da86-4377-85ce-7a59abccbdf9\",\"_breakpoint\":false,\"targetExpression\":\"expression:groovy:message.properties[\'reviews\'] = result.payload\",\"target\":\"urn:c8ea1ead-9af4-4131-b980-02ece85fc08a\",\"type\":\"ALL_CONNECTION\"}},{\"id\":\"14f5321b-ffad-43ae-a592-a2d45b249b7a\",\"name\":\"ALL_CONNECTION\",\"properties\":{\"points\":\"379,250;411,250;\",\"source\":\"urn:0e1f3c7f-dacd-4415-b122-39e5a54601e1\",\"_breakpoint\":false,\"targetExpression\":\"expression:groovy:message.properties[\'rank\'] = result.payload\",\"target\":\"urn:055cfbce-3ca4-4553-8a47-a6cf347eec2c\",\"type\":\"ALL_CONNECTION\"}},{\"id\":\"3e367679-4b6d-4c1b-a989-b5b4e2a629e8\",\"name\":\"ALL_CONNECTION\",\"properties\":{\"points\":\"328,261;323,295;254,310;\",\"source\":\"urn:0e1f3c7f-dacd-4415-b122-39e5a54601e1\",\"_breakpoint\":false,\"targetExpression\":\"expression:groovy:message.properties[\'productInfo\'] = result.payload\",\"target\":\"urn:1321cec6-204d-4359-bb79-aed246ad3b4d\",\"type\":\"ALL_CONNECTION\"}},{\"id\":\"0c884692-3ce4-47e3-b0c3-3d3f4c250828\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"809,321;862,321;\",\"source\":\"urn:774222a0-06d4-4e49-adbc-20c81a9fc7c4\",\"_breakpoint\":false,\"target\":\"urn:db2e8a5d-8649-488c-b693-dbe36f648a24\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"dd563e1a-d9e5-4c7c-8687-6e53909c2678\",\"name\":\"CHAIN_CONNECTION\",\"properties\":{\"points\":\"709,261;742,310;\",\"source\":\"urn:1e15de73-e6ac-4ea1-82d6-e5251fafa994\",\"_breakpoint\":false,\"target\":\"urn:774222a0-06d4-4e49-adbc-20c81a9fc7c4\",\"type\":\"CHAIN_CONNECTION\"}},{\"id\":\"b5bab5ab-72e7-40f7-9eca-9454d68030c9\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"263,190;232,190;\",\"source\":\"urn:f2000017-d7cc-47cc-a49b-a24f4760baa3\",\"_breakpoint\":false,\"target\":\"urn:e6d00e52-5e31-4e96-a266-7fc734386049\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"4cc5a122-c95e-409a-8cb2-f99f712b66bd\",\"name\":\"ALL_CONNECTION\",\"properties\":{\"points\":\"867,239;935,219;\",\"source\":\"urn:cc80ea9b-da86-4377-85ce-7a59abccbdf9\",\"_breakpoint\":false,\"targetExpression\":\"expression:groovy:message.properties[\'reputation\'] = result.payload\",\"target\":\"urn:ac39acb7-ff73-425d-87c8-0908c796e0ee\",\"type\":\"ALL_CONNECTION\"}},{\"id\":\"abe4d086-db9e-49ca-8e2e-046b2ba41af7\",\"name\":\"CHOICE_CONNECTION\",\"properties\":{\"points\":\"598,261;612,310;\",\"source\":\"urn:81203195-e11c-4741-b93f-bc14ed5e5368\",\"_breakpoint\":false,\"target\":\"urn:3a901bed-4674-485d-a4be-9018d6a73612\",\"type\":\"CHOICE_CONNECTION\"}},{\"id\":\"ff8f2778-103c-45dc-834a-3464b68d2dcb\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"132,250;186,250;\",\"source\":\"urn:6482f58e-3e67-4414-8f73-b059dd25f6d5\",\"_breakpoint\":false,\"target\":\"urn:b86628ad-0ac6-4cf2-a196-98deb940ac53\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"df18d708-543d-498c-9393-53ab6f96ac52\",\"name\":\"CHOICE_CONNECTION\",\"properties\":{\"points\":\"638,250;672,250;\",\"source\":\"urn:81203195-e11c-4741-b93f-bc14ed5e5368\",\"_breakpoint\":false,\"target\":\"urn:1e15de73-e6ac-4ea1-82d6-e5251fafa994\",\"type\":\"CHOICE_CONNECTION\"}},{\"id\":\"195fb13c-9379-4781-8448-ff50f715e222\",\"name\":\"WIRE_TAP_CONNECTION\",\"properties\":{\"points\":\"628,310;688,261;\",\"source\":\"urn:3a901bed-4674-485d-a4be-9018d6a73612\",\"_breakpoint\":false,\"target\":\"urn:1e15de73-e6ac-4ea1-82d6-e5251fafa994\",\"type\":\"WIRE_TAP_CONNECTION\"}},{\"id\":\"d693ed94-a7b5-46a4-985a-ad50dd9a0197\",\"name\":\"CHAIN_CONNECTION\",\"properties\":{\"points\":\"731,250;772,250;\",\"source\":\"urn:1e15de73-e6ac-4ea1-82d6-e5251fafa994\",\"_breakpoint\":false,\"target\":\"urn:cc80ea9b-da86-4377-85ce-7a59abccbdf9\",\"type\":\"CHAIN_CONNECTION\"}},{\"id\":\"dca9d407-8e17-4da6-a7ae-81327cb48fd4\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"532,250;553,250;\",\"source\":\"urn:055cfbce-3ca4-4553-8a47-a6cf347eec2c\",\"_breakpoint\":false,\"target\":\"urn:81203195-e11c-4741-b93f-bc14ed5e5368\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"b8a03675-a10c-41db-bcfe-badee3d66396\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"888,250;939,250;\",\"source\":\"urn:cc80ea9b-da86-4377-85ce-7a59abccbdf9\",\"_breakpoint\":false,\"target\":\"urn:9894b25c-ec59-4a05-b129-233ccc51f186\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}}]}',3,NULL);
INSERT INTO `flows` (`flow_id`, `name`, `json_representation`, `application_id`, `application_snapshot_id`) VALUES (22,'c7940ddb-497e-4b14-98e7-67bdf953ed9a','{\"id\":\"c7940ddb-497e-4b14-98e7-67bdf953ed9a\",\"version\":1,\"definitions\":[{\"id\":\"b86628ad-0ac6-4cf2-a196-98deb940ac53\",\"name\":\"CHOICE\",\"properties\":{\"__connectionsCHOICE_CONNECTION\":\"list(urn:051a488b-7eeb-491c-9749-9f2f49f7c7c7)\",\"name\":\"Path router\",\"_breakpoint\":false,\"x\":\"186\",\"y\":\"239\"}},{\"id\":\"1321cec6-204d-4359-bb79-aed246ad3b4d\",\"name\":\"SQL_SELECT\",\"properties\":{\"name\":\"Get product info\",\"selectExpression\":\"\'SELECT name, price FROM amazon.product where product_oid = ?\'\",\"_breakpoint\":false,\"x\":\"181\",\"y\":\"310\",\"parameterExpressions\":\"message.properties[\'actions.http.productId\']\"}},{\"id\":\"e6d00e52-5e31-4e96-a266-7fc734386049\",\"name\":\"DUST_RENDERER\",\"properties\":{\"template\":\"\'Product: {name} <br> Price: {price} <br> Rank: {rank}\'\",\"name\":\"Render template\",\"_breakpoint\":false,\"jsonData\":\"message.payload\",\"x\":\"115\",\"y\":\"179\"}},{\"id\":\"0e1f3c7f-dacd-4415-b122-39e5a54601e1\",\"name\":\"ALL\",\"properties\":{\"__next_in_chain\":\"urn:df9a2410-be36-4206-a014-5e6a2a1dac02\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:ee6f5a13-bd22-465c-bc61-5ed3d0fd306b,urn:40ab8865-d169-4616-bb71-d70013beaeaf,urn:df9a2410-be36-4206-a014-5e6a2a1dac02)\",\"name\":\"Get info\",\"_breakpoint\":false,\"x\":\"311\",\"y\":\"239\",\"__connectionsALL_CONNECTION\":\"list(urn:81c05ce5-f6a6-4ee2-9035-f9c3a751c092,urn:c47939d2-3faa-4bff-a6e7-744484bf30f1,urn:30e28538-fa52-4a45-b2db-d6737bc2e544,urn:bf9cab2b-778f-4462-b3c1-9f886b8c897d,urn:6412eea3-99e9-4e0a-8d2c-be9bd2008c2a,urn:3e367679-4b6d-4c1b-a989-b5b4e2a629e8,urn:55944e08-1004-421c-b613-d9d6919c6174,urn:1f65e8df-7761-4c4b-8658-b9f777281a3c,urn:14f5321b-ffad-43ae-a592-a2d45b249b7a,urn:fa19f353-927b-451a-9901-cab49aa6855c)\"}},{\"id\":\"cc80ea9b-da86-4377-85ce-7a59abccbdf9\",\"name\":\"ALL\",\"properties\":{\"__next_in_chain\":\"urn:b8a03675-a10c-41db-bcfe-badee3d66396\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:b8a03675-a10c-41db-bcfe-badee3d66396)\",\"name\":\"Get product rank\",\"_breakpoint\":false,\"x\":\"443\",\"y\":\"239\",\"__connectionsALL_CONNECTION\":\"list(urn:4cc5a122-c95e-409a-8cb2-f99f712b66bd,urn:099dedee-f7d3-4758-af6d-04ea3dfbd82b)\"}},{\"id\":\"f2000017-d7cc-47cc-a49b-a24f4760baa3\",\"name\":\"GROOVY\",\"properties\":{\"__next_in_chain\":\"urn:b5bab5ab-72e7-40f7-9eca-9454d68030c9\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:5007efd0-02e1-47bd-baea-0fedca999dfe,urn:b5bab5ab-72e7-40f7-9eca-9454d68030c9)\",\"name\":\"Compose data\",\"_breakpoint\":false,\"x\":\"263\",\"y\":\"179\",\"script\":\"message.payload = \'{\\\"name\\\":\\\"\' + message.properties[\'productInfo\'][0][0] + \'\\\",\\\"price\\\":\\\"\' + message.properties[\'productInfo\'][0][1] + \'\\\",\\\"rank\\\":\\\"\' + message.properties[\'rank\'] + \'\\\"}\'\"}},{\"id\":\"9894b25c-ec59-4a05-b129-233ccc51f186\",\"name\":\"GROOVY\",\"properties\":{\"__next_in_chain\":\"urn:c951d90b-6900-4794-bf4a-a2216936053f\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:c951d90b-6900-4794-bf4a-a2216936053f)\",\"name\":\"Compute product rank\",\"_breakpoint\":false,\"x\":\"610\",\"y\":\"239\",\"script\":\"def reputationMapping = []; def totalReputation = 0; def userReputationResult = message.properties[\'reputation\'];  for (row in userReputationResult) { \\treputationMapping[row[0]] = row[1]; \\ttotalReputation = row[1]; };  def computedRank = 0; def reviewResult = message.properties[\'reviews\']; \\t  for (row in reviewResult) { \\tif (reputationMapping[row[0]] != null) { \\t\\tdef reputation = reputationMapping[row[0]]; \\t\\tcomputedRank = computedRank + (reputation * row[1]); \\t}; }; message.payload = computedRank;\"}},{\"id\":\"c8ea1ead-9af4-4131-b980-02ece85fc08a\",\"name\":\"SQL_SELECT\",\"properties\":{\"name\":\"Get product reviews\",\"selectExpression\":\"\'SELECT review.user_oid, review.value FROM review WHERE review.product_product_oid = ?\'\",\"_breakpoint\":false,\"x\":\"578\",\"y\":\"277\",\"parameterExpressions\":\"message.properties[\'actions.http.productId\']\"}},{\"id\":\"6482f58e-3e67-4414-8f73-b059dd25f6d5\",\"name\":\"HTTP_MESSAGE_SOURCE\",\"properties\":{\"__next_in_chain\":\"urn:ff8f2778-103c-45dc-834a-3464b68d2dcb\",\"port\":\"8822\",\"timeoutExpression\":\"-1l\",\"__connectionsNEXT_IN_CHAIN_CONNECTION\":\"list(urn:ff8f2778-103c-45dc-834a-3464b68d2dcb)\",\"name\":\"HTTP listener\",\"_breakpoint\":false,\"x\":\"32\",\"y\":\"239\"}},{\"id\":\"ac39acb7-ff73-425d-87c8-0908c796e0ee\",\"name\":\"SQL_SELECT\",\"properties\":{\"name\":\"Get user reputation\",\"selectExpression\":\"\'SELECT review.user_oid, sum(valuation.value * (365 \\/ (DATEDIFF(NOW(), valuation.valuation_date) + 1))) as user_valuation FROM review inner join valuation on (review.review_oid = valuation.review_review_oid) WHERE review.user_oid IN (select review.user_oid from review where product_product_oid = ?) GROUP BY review.user_oid\'\",\"_breakpoint\":false,\"x\":\"578\",\"y\":\"197\",\"parameterExpressions\":\"message.properties[\'actions.http.productId\']\"}},{\"id\":\"df9a2410-be36-4206-a014-5e6a2a1dac02\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"340,239;321,201;\",\"source\":\"urn:0e1f3c7f-dacd-4415-b122-39e5a54601e1\",\"_breakpoint\":false,\"target\":\"urn:f2000017-d7cc-47cc-a49b-a24f4760baa3\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"099dedee-f7d3-4758-af6d-04ea3dfbd82b\",\"name\":\"ALL_CONNECTION\",\"properties\":{\"points\":\"543,261;603,277;\",\"source\":\"urn:cc80ea9b-da86-4377-85ce-7a59abccbdf9\",\"_breakpoint\":false,\"targetExpression\":\"expression:groovy:message.properties[\'reviews\'] = result.payload\",\"target\":\"urn:c8ea1ead-9af4-4131-b980-02ece85fc08a\",\"type\":\"ALL_CONNECTION\"}},{\"id\":\"051a488b-7eeb-491c-9749-9f2f49f7c7c7\",\"name\":\"CHOICE_CONNECTION\",\"properties\":{\"points\":\"272,250;311,250;\",\"expression\":\"message.properties[\'actions.http.requestURI\'].startsWith(\'\\/product\\/\')\",\"source\":\"urn:b86628ad-0ac6-4cf2-a196-98deb940ac53\",\"_breakpoint\":false,\"target\":\"urn:0e1f3c7f-dacd-4415-b122-39e5a54601e1\",\"type\":\"CHOICE_CONNECTION\"}},{\"id\":\"3e367679-4b6d-4c1b-a989-b5b4e2a629e8\",\"name\":\"ALL_CONNECTION\",\"properties\":{\"points\":\"328,261;323,295;254,310;\",\"source\":\"urn:0e1f3c7f-dacd-4415-b122-39e5a54601e1\",\"_breakpoint\":false,\"targetExpression\":\"expression:groovy:message.properties[\'productInfo\'] = result.payload\",\"target\":\"urn:1321cec6-204d-4359-bb79-aed246ad3b4d\",\"type\":\"ALL_CONNECTION\"}},{\"id\":\"b5bab5ab-72e7-40f7-9eca-9454d68030c9\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"263,190;232,190;\",\"source\":\"urn:f2000017-d7cc-47cc-a49b-a24f4760baa3\",\"_breakpoint\":false,\"target\":\"urn:e6d00e52-5e31-4e96-a266-7fc734386049\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"4cc5a122-c95e-409a-8cb2-f99f712b66bd\",\"name\":\"ALL_CONNECTION\",\"properties\":{\"points\":\"538,239;606,219;\",\"source\":\"urn:cc80ea9b-da86-4377-85ce-7a59abccbdf9\",\"_breakpoint\":false,\"targetExpression\":\"expression:groovy:message.properties[\'reputation\'] = result.payload\",\"target\":\"urn:ac39acb7-ff73-425d-87c8-0908c796e0ee\",\"type\":\"ALL_CONNECTION\"}},{\"id\":\"ff8f2778-103c-45dc-834a-3464b68d2dcb\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"132,250;186,250;\",\"source\":\"urn:6482f58e-3e67-4414-8f73-b059dd25f6d5\",\"_breakpoint\":false,\"target\":\"urn:b86628ad-0ac6-4cf2-a196-98deb940ac53\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"b8a03675-a10c-41db-bcfe-badee3d66396\",\"name\":\"NEXT_IN_CHAIN_CONNECTION\",\"properties\":{\"points\":\"559,250;610,250;\",\"source\":\"urn:cc80ea9b-da86-4377-85ce-7a59abccbdf9\",\"_breakpoint\":false,\"target\":\"urn:9894b25c-ec59-4a05-b129-233ccc51f186\",\"type\":\"NEXT_IN_CHAIN_CONNECTION\"}},{\"id\":\"fa19f353-927b-451a-9901-cab49aa6855c\",\"name\":\"ALL_CONNECTION\",\"properties\":{\"points\":\"379,250;443,250;\",\"source\":\"urn:0e1f3c7f-dacd-4415-b122-39e5a54601e1\",\"_breakpoint\":false,\"targetExpression\":\"expression:groovy:message.properties[\'rank\'] = result.payload\",\"target\":\"urn:cc80ea9b-da86-4377-85ce-7a59abccbdf9\",\"type\":\"ALL_CONNECTION\"}}]}',3,NULL);
