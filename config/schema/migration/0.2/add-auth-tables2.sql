USE `actions`;

SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE `team`
DROP FOREIGN KEY `FK_frjxgaag9ypwhjyf7y3p5o3of`;

ALTER TABLE `user_team`
DROP FOREIGN KEY `FK_1jb2s2dtt02g96kybm2jvtorj`;

DROP TABLE IF EXISTS `abstraction_user`; 
DROP TABLE IF EXISTS `actions_users`;
DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `user_roles`;


CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(45) NOT NULL UNIQUE,
  `password` VARCHAR(45) NOT NULL,
  `enabled` bit(1)  NOT NULL,
  `confirmed` bit(1)  NOT NULL,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `first_name` VARCHAR(200),
  `last_name` VARCHAR(200),
  `creationDate` TIMESTAMP NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `authorities` (
  `auth_id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` bigint(20) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  foreign key (user_id) references users (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE user_team
ADD CONSTRAINT fk_user_id
FOREIGN KEY (user_id)
REFERENCES `users`(`user_id`);

ALTER TABLE `team`
ADD CONSTRAINT fk_owner_id
FOREIGN KEY (`owner_id`)
REFERENCES `users`(`user_id`);

DROP TABLE IF EXISTS `user`;

INSERT INTO `users` (`username`, `password`, `enabled`,`confirmed`, `email`, `first_name`, `last_name`, `creationDate`)
VALUES
  ('admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 1, 1, "admin@admin.com", "Admin", "Admin", NOW()),
  ('guido', 'adfc8f0aec273eace2629141317c1eac53923f28', 1, 1, "admin1@admin.com", "Guido", "Celada", NOW()),
  ('esteban', '431ef4c5da9a59cbbe78df77a53fcc737a3b78fa', 1, 1, "admin2@admin.com", "Esteban", "Robles Luna", NOW()),
  ('matias', 'dbc6bbe3b711e3f58f25638428fdfc12e34c8c38', 1, 1, "admin3@admin.com", "Matias", "Rivero", NOW()),
  ('disabled', '07596f183f5e91b1778d5e47b2752b8d42aa763d', 0, 1, "admin4@admin.com", "Disabled", "User Test", NOW()),
  ('grayfox', 'd8eafdf804a8d4f3894f1b10430f176418b251bf', 1, 1, "admin5@admin.com", "Jerónimo", "Spuri", NOW());


INSERT INTO `authorities` (`authority`, `user_id`)
VALUES
  ('ROLE_ADMIN', '1'),
  ('ROLE_USER', '2'),
  ('ROLE_USER', '3'),
  ('ROLE_USER', '4'),
  ('ROLE_USER', '5'),
  ('ROLE_USER', '6');

SET FOREIGN_KEY_CHECKS = 1;
