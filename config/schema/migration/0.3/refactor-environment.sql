
ALTER TABLE `actions`.`property` DROP COLUMN `environment` ;

ALTER TABLE `actions`.`property` ADD COLUMN `environment` VARCHAR(45) NULL
, ADD INDEX `PROPERTY_ENV_IND` (`environment` ASC) ;


ALTER TABLE `actions`.`application_snapshot` DROP COLUMN `environment` ;

ALTER TABLE `actions`.`application_snapshot` ADD COLUMN `environment` VARCHAR(45) NULL
, ADD INDEX `PROPERTY_ENV_IND` (`environment` ASC) ;