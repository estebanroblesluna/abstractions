USE `actions`;

CREATE  TABLE `actions`.`profiling_info` (
  `profiling_info_id` BIGINT NOT NULL AUTO_INCREMENT ,
  `date` DATETIME NULL ,
  `application_id` VARCHAR(200) NULL ,
  PRIMARY KEY (`profiling_info_id`) ,
  INDEX `PROFILING_NAME_DATE` (`application_id` ASC, `date` ASC) );

  
CREATE  TABLE `actions`.`profiling_info_averages` (
  `profiling_info_averages_id` BIGINT NOT NULL AUTO_INCREMENT ,
  `profiling_info_id` BIGINT NULL ,
  `element_id` VARCHAR(200) NULL ,
  `average` DOUBLE NULL ,
  PRIMARY KEY (`profiling_info_averages_id`) ,
  INDEX `FK_PROFILING_INFO_idx` (`profiling_info_id` ASC) ,
  CONSTRAINT `FK_PROFILING_INFO`
    FOREIGN KEY (`profiling_info_id` )
    REFERENCES `actions`.`profiling_info` (`profiling_info_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    
ALTER TABLE `actions`.`profiling_info_averages` 
ADD INDEX `PROFILING_INFO_AVERAGE` (`average` ASC) ;
