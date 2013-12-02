USE `actions`;

DROP TABLE IF EXISTS `abstraction_user`;
DROP TABLE IF EXISTS `user_roles`;


CREATE TABLE `user_roles` (
  `user_role_id` INT(20) UNSIGNED NOT NULL,
  `authority` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `abstraction_user` (
  `user_id` INT(20) UNSIGNED NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `user_role_id` INT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK_user_role_id` (`user_role_id`),
  CONSTRAINT `FK_user_role_id` FOREIGN KEY (`user_role_id`) REFERENCES `user_roles` (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO `user_roles` (`user_role_id`, `authority`)
VALUES
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_USER');

INSERT INTO `abstraction_user` (`user_id`, `username`, `password`, `enabled`, `user_role_id`)
VALUES
  (1, 'admin', 'admin', 1, 1),
  (2, 'guido', 'guido', 1, 2),
  (3, 'esteban', 'esteban', 1, 2),
  (4, 'matias', 'matias', 1, 2),
  (5, 'disabled', 'disabled', 0, 2);