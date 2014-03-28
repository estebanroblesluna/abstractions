ALTER TABLE `actions`.`server` ADD COLUMN `external_id` VARCHAR(80) NULL  AFTER `last_update` ;

UPDATE `actions`.`server` SET `external_id` = 1 WHERE `server_id` = 1;