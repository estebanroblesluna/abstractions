ALTER TABLE `actions`.`deployment` DROP FOREIGN KEY `FK_6r71apk88qrvikactmjollkhu`;

ALTER TABLE `actions`.`deployment` 
  ADD CONSTRAINT `fk_deployment_user`
  FOREIGN KEY (`trigger_by_user_id` )
  REFERENCES `actions`.`users` (`user_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_deployment_user_idx` (`trigger_by_user_id` ASC);