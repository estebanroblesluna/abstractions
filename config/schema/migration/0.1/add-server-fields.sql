USE `actions`;

ALTER TABLE `actions`.`server` 
ADD COLUMN `server_key` VARCHAR(80) NULL  AFTER `server_group_id` , 
ADD COLUMN `last_update` TIMESTAMP NULL  AFTER `server_key` , 
ADD UNIQUE INDEX `server_key_UNIQUE` (`server_key` ASC);
