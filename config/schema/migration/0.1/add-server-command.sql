USE `actions`;

CREATE  TABLE `actions`.`server_commands` (
  `server_command_id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(80) NULL ,
  `state` VARCHAR(45) NULL ,
  `deployment_to_server_id` BIGINT NULL ,
  PRIMARY KEY (`server_command_id`) ,
  INDEX `SERVER_COMMAND_STATE` (`state` ASC) ,
  INDEX `SERVER_COMMAND_DEPLOYMENT_idx` (`deployment_to_server_id` ASC) ,
  CONSTRAINT `SERVER_COMMAND_DEPLOYMENT`
    FOREIGN KEY (`deployment_to_server_id` )
    REFERENCES `actions`.`deployment_to_server` (`deployment_to_server_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE  TABLE `actions`.`server_commands_arguments` (
  `server_command_id` INT NULL ,
  `key` VARCHAR(80) NOT NULL ,
  `value` VARCHAR(255) NULL ,
  PRIMARY KEY (`server_command_id`, `key`) );
