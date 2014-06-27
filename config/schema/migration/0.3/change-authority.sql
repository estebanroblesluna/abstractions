ALTER TABLE `actions`.`authorities` DROP FOREIGN KEY `authorities_ibfk_1` ;
ALTER TABLE `actions`.`authorities` CHANGE COLUMN `user_id` `user_id` BIGINT(20) NULL  , 
  ADD CONSTRAINT `authorities_ibfk_1`
  FOREIGN KEY (`user_id` )
  REFERENCES `actions`.`users` (`user_id` );
