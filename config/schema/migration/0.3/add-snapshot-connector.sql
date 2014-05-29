ALTER TABLE `actions`.`connector` ADD COLUMN `application_snapshot_id` BIGINT NULL  AFTER `team_id` , 
  ADD CONSTRAINT `fk_connector_application_snapshot`
  FOREIGN KEY (`application_snapshot_id` )
  REFERENCES `actions`.`application_snapshot` (`application_snapshot_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_connector_application_snapshot_idx` (`application_snapshot_id` ASC) ;