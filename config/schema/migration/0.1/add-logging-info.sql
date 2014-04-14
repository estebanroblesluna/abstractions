USE `actions`;

CREATE  TABLE `actions`.`logging_info` (
  `logging_info_id` BIGINT NOT NULL AUTO_INCREMENT ,
  `date` DATETIME NULL ,
  `application_id` VARCHAR(200) NULL ,
  PRIMARY KEY (`logging_info_id`) ,
  INDEX `PROFILING_NAME_DATE` (`application_id` ASC, `date` ASC) );

  
CREATE  TABLE `actions`.`logging_info_logs` (
  `logging_info_logs_id` BIGINT NOT NULL AUTO_INCREMENT ,
  `logging_info_id` BIGINT NULL ,
  `element_id` VARCHAR(200) NULL ,
  `log` TEXT NULL ,
  PRIMARY KEY (`logging_info_logs_id`) ,
  INDEX `FK_LOGGING_INFO_idx` (`logging_info_id` ASC) ,
  CONSTRAINT `FK_LOGGING_INFO`
    FOREIGN KEY (`logging_info_id` )
    REFERENCES `actions`.`logging_info` (`logging_info_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
