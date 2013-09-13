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
  `environment` tinyblob,
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
  `deployment_id` bigint(20) DEFAULT NULL,
  `server_group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`server_id`),
  KEY `FK_eo9q7kit1fq595i1nqylkyte7` (`deployment_id`),
  KEY `FK_pqtufjlc2ppjsc9rnt5qade2u` (`server_group_id`),
  CONSTRAINT `FK_pqtufjlc2ppjsc9rnt5qade2u` FOREIGN KEY (`server_group_id`) REFERENCES `server_group` (`server_group_id`),
  CONSTRAINT `FK_eo9q7kit1fq595i1nqylkyte7` FOREIGN KEY (`deployment_id`) REFERENCES `deployment` (`deployment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property` (
  `property_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `environment` tinyblob,
  `application_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`property_id`),
  KEY `FK_ouwdmllkcrsympjv9o04i0igr` (`application_id`),
  CONSTRAINT `FK_ouwdmllkcrsympjv9o04i0igr` FOREIGN KEY (`application_id`) REFERENCES `application` (`application_id`)
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
  primary key (flow_id),
  KEY `FK_ouwdmllkcrsympjv9o04i0hgr` (`application_id`),
  CONSTRAINT `FK_ouwdmllkcrsympjv9o04i0hgr` FOREIGN KEY (`application_id`) REFERENCES `application` (`application_id`)
);