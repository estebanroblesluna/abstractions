USE `actions`;


DROP TABLE IF EXISTS `server_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server_stats` (
  `server_stats_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `server_id` bigint(20) NOT NULL,
  `context_id` varchar(100) DEFAULT NULL,
  `date` timestamp NULL,
  `uncaught_exceptions` bigint(20) NOT NULL,
  PRIMARY KEY (`server_stats_id`),
  KEY `FK_frjxgaag9ypwhjyf7y4p5o3of` (`server_id`),
  CONSTRAINT `FK_frjxgaag9ypwhjyf7y4p5o3of` FOREIGN KEY (`server_id`) REFERENCES `server` (`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `server_stats_received_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server_stats_received_messages` (
  `server_stats_id` bigint(20),
  `messages_received` bigint(20) NOT NULL,
  `message_source` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
