USE `actions`;

DROP TABLE IF EXISTS `icon`;
CREATE TABLE icon (
  `icon_id` bigint(20) NOT NULL AUTO_INCREMENT,
  image blob NULL,
  primary key (icon_id)
);

ALTER TABLE `element_definition`
ADD `icon_id` bigint(20),
ADD KEY `FK_icon` (`icon_id`),
ADD CONSTRAINT `FK_icon` FOREIGN KEY (`icon_id`) REFERENCES `icon` (`icon_id`);