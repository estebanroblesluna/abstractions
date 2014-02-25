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
  (1, 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 1, 1),
  (2, 'guido', 'adfc8f0aec273eace2629141317c1eac53923f28', 1, 2),
  (3, 'esteban', '431ef4c5da9a59cbbe78df77a53fcc737a3b78fa', 1, 2),
  (4, 'matias', 'dbc6bbe3b711e3f58f25638428fdfc12e34c8c38', 1, 2),
  (5, 'disabled', '07596f183f5e91b1778d5e47b2752b8d42aa763d', 0, 2),
  (6, 'grayfox', 'd8eafdf804a8d4f3894f1b10430f176418b251bf', 1, 2);
