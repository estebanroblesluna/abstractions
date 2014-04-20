USE `actions`;

ALTER TABLE `actions`.`resource` CHANGE COLUMN `data` `data` LONGBLOB NOT NULL;
