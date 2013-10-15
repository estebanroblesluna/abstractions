USE `actions`;


DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `team_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`team_id`),
  KEY `FK_frjxgaag9ypwhjyf7y3p5o3of` (`owner_id`),
  CONSTRAINT `FK_frjxgaag9ypwhjyf7y3p5o3of` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `application_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `team_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`application_id`),
  KEY `FK_i1txg9ron2idn45hk3c0i194b` (`team_id`),
  CONSTRAINT `FK_i1txg9ron2idn45hk3c0i194b` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `application_snapshot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_snapshot` (
  `application_snapshot_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `application_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`application_snapshot_id`),
  KEY `FK_ml7xwp7hik39xkt92yr5chk1r` (`application_id`),
  CONSTRAINT `FK_ml7xwp7hik39xkt92yr5chk1r` FOREIGN KEY (`application_id`) REFERENCES `application` (`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `deployment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deployment` (
  `deployment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trigger_by_user_id` bigint(20) DEFAULT NULL,
  `application_snapshot_id` bigint(20) DEFAULT NULL,
  `application_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`deployment_id`),
  KEY `FK_6r71apk88qrvikactmjollkhu` (`trigger_by_user_id`),
  KEY `FK_l28mht8yc24ch5p7e1red2ony` (`application_snapshot_id`),
  KEY `FK_93eigd9x8wjmovsxx1cm49b3w` (`application_id`),
  CONSTRAINT `FK_93eigd9x8wjmovsxx1cm49b3w` FOREIGN KEY (`application_id`) REFERENCES `application` (`application_id`),
  CONSTRAINT `FK_6r71apk88qrvikactmjollkhu` FOREIGN KEY (`trigger_by_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_l28mht8yc24ch5p7e1red2ony` FOREIGN KEY (`application_snapshot_id`) REFERENCES `application_snapshot` (`application_snapshot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `server_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server_group` (
  `server_group_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `environment` varchar(20),
  `team_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`server_group_id`),
  KEY `FK_lk0ip0o5wh0qcqnv0rw22dq2` (`team_id`),
  CONSTRAINT `FK_lk0ip0o5wh0qcqnv0rw22dq2` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server` (
  `server_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `ipDNS` varchar(255) DEFAULT NULL,
  `port` int DEFAULT -1,
  `server_group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`server_id`),
  KEY `FK_pqtufjlc2ppjsc9rnt5qade2u` (`server_group_id`),
  CONSTRAINT `FK_pqtufjlc2ppjsc9rnt5qade2u` FOREIGN KEY (`server_group_id`) REFERENCES `server_group` (`server_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS  `actions`.`deployment_to_server`;
CREATE TABLE `actions`.`deployment_to_server` (
  `deployment_to_server_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_update` TIMESTAMP NULL, 
  `deployment_state` varchar(50) DEFAULT NULL,
  `server_id` bigint(20) NOT NULL,
  `deployment_id` bigint(20) NOT NULL,
  PRIMARY KEY (`deployment_to_server_id`),
  KEY `FK_6r71apk88qrvhkactmjollkhu` (`server_id`),
  KEY `FK_l28mht8yc24cj5p7e1red2ony` (`deployment_id`),
  CONSTRAINT `FK_6r71apk88qrvhkactmjollkhu` FOREIGN KEY (`server_id`) REFERENCES `server` (`server_id`),
  CONSTRAINT `FK_l28mht8yc24cj5p7e1red2ony` FOREIGN KEY (`deployment_id`) REFERENCES `deployment` (`deployment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property` (
  `property_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `environment` tinyblob,
  `application_id` bigint(20) DEFAULT NULL,
  `application_snapshot_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`property_id`),
  KEY `FK_ouwdmllkcrsympjv9o04i0igr` (`application_id`),
  CONSTRAINT `FK_ouwdmllkcrsympjv9o04i0igr` FOREIGN KEY (`application_id`) REFERENCES `application` (`application_id`),
  KEY `FK_ouwdmllkcrsympjv4o04i0igr` (`application_snapshot_id`),
  CONSTRAINT `FK_ouwdmllkcrsympjv4o04i0igr` FOREIGN KEY (`application_snapshot_id`) REFERENCES `application_snapshot` (`application_snapshot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `user_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_team` (
  `team_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  KEY `FK_1jb2s2dtt02g96kybm2jvtorj` (`user_id`),
  KEY `FK_keqg9kiqm4mga7l6u8c1a9898` (`team_id`),
  CONSTRAINT `FK_keqg9kiqm4mga7l6u8c1a9898` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`),
  CONSTRAINT `FK_1jb2s2dtt02g96kybm2jvtorj` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `flows`;

CREATE TABLE flows (
  `flow_id` bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  json_representation TEXT,
  `application_id` bigint(20) DEFAULT NULL,
  `application_snapshot_id` bigint(20) DEFAULT NULL,
  primary key (flow_id),
  KEY `FK_ouwdmllkcrsympjv9o04i0hgr` (`application_id`),
  CONSTRAINT `FK_ouwdmllkcrsympjv9o04i0hgr` FOREIGN KEY (`application_id`) REFERENCES `application` (`application_id`),
  KEY `FK_ouwdmllkcrsympjv3o04i0igr` (`application_snapshot_id`),
  CONSTRAINT `FK_ouwdmllkcrsympjv3o04i0igr` FOREIGN KEY (`application_snapshot_id`) REFERENCES `application_snapshot` (`application_snapshot_id`)
);


DROP TABLE IF EXISTS `library`;

CREATE TABLE library (
  `library_id` bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(200) NOT NULL,
  display_name varchar(200) NOT NULL,
  primary key (library_id)
);


DROP TABLE IF EXISTS `element_definition`;
CREATE TABLE element_definition (
  `element_definition_id` bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(200) NOT NULL,
  display_name varchar(200) NULL,
  icon varchar(200) NULL,
  implementation varchar(200) NULL,
  `library_id` bigint(20) DEFAULT NULL,
  is_script boolean NULL,
  primary key (element_definition_id),
  KEY `FK_ouwdmllmcrsympjv9o04i0hgr` (`library_id`),
  CONSTRAINT `FK_ouwdmllmcrsympjv9o04i0hgr` FOREIGN KEY (`library_id`) REFERENCES `library` (`library_id`)
);


DROP TABLE IF EXISTS `connection_definition`;
CREATE TABLE connection_definition (
  `element_definition_id` bigint(20) NOT NULL,
  color varchar(200) NOT NULL,
  accepted_source_types varchar(2000) NULL,
  accepted_target_types varchar(2000) NULL,
  accepted_source_max int NULL,
  accepted_target_max int NULL,
  primary key (element_definition_id),
  CONSTRAINT `FK_ouwdmllmcrsympjv9o14i0hgr` FOREIGN KEY (`element_definition_id`) REFERENCES `element_definition` (`element_definition_id`)
);


DROP TABLE IF EXISTS `flow_definition`;
CREATE TABLE flow_definition (
  `element_definition_id` bigint(20) NOT NULL,
  primary key (element_definition_id),
  CONSTRAINT `FK_ouwdmlbmcrsympjv9o14i0hgr` FOREIGN KEY (`element_definition_id`) REFERENCES `element_definition` (`element_definition_id`)
);


DROP TABLE IF EXISTS `message_source_definition`;
CREATE TABLE message_source_definition (
  `element_definition_id` bigint(20) NOT NULL,
  primary key (element_definition_id),
  CONSTRAINT `FK_auwdmlbmcrsympjv9o14i0hgr` FOREIGN KEY (`element_definition_id`) REFERENCES `element_definition` (`element_definition_id`)
);
    

DROP TABLE IF EXISTS `processor_definition`;
CREATE TABLE processor_definition (
  `element_definition_id` bigint(20) NOT NULL,
  primary key (element_definition_id),
  CONSTRAINT `FK_auwdmlbmdrsympjv9o14i0hgr` FOREIGN KEY (`element_definition_id`) REFERENCES `element_definition` (`element_definition_id`)
);


DROP TABLE IF EXISTS `router_definition`;
CREATE TABLE router_definition (
  `element_definition_id` bigint(20) NOT NULL,
  router_evaluator_implementation varchar(200) NOT NULL,
  is_router_evaluator_script boolean NOT NULL,
  primary key (element_definition_id),
  CONSTRAINT `FK_auwdmlbmdrszmpjv9o14i0hgr` FOREIGN KEY (`element_definition_id`) REFERENCES `element_definition` (`element_definition_id`)
);


DROP TABLE IF EXISTS `element_property_definition`;
CREATE TABLE element_property_definition (
  `property_definition_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `element_definition_id` bigint(20) NULL,
  name varchar(200) NOT NULL,
  display_name varchar(200) NULL,
  property_type varchar(200) NULL,
  default_value varchar(200) NULL,
  primary key (property_definition_id),
  CONSTRAINT `FK_auwamlbmdrszmpjv9o14i0hgr` FOREIGN KEY (`element_definition_id`) REFERENCES `element_definition` (`element_definition_id`)
);