USE `actions`;

DROP TABLE IF EXISTS `abstraction_user`; 
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `authorities`;

CREATE TABLE `users` (
  `username` VARCHAR(45) NOT NULL PRIMARY KEY,
  `password` VARCHAR(45) NOT NULL,
  `enabled` boolean NOT NULL,
  `confirmed` boolean NOT NULL,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `full_name` VARCHAR(200),
  `creation_date` TIMESTAMP NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `authorities` (
  `username` VARCHAR(50) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  foreign key (username) references users (username),
  unique index authorities_idx_1 (username, authority)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `users` (`username`, `password`, `enabled`,`confirmed`, `email`, `full_name`, `creation_date`)
VALUES
  ('admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 1, 1, "admin@admin.com", "admin admin", NOW()),
  ('guido', 'adfc8f0aec273eace2629141317c1eac53923f28', 1, 1, "admin1@admin.com", "admin admin", NOW()),
  ('esteban', '431ef4c5da9a59cbbe78df77a53fcc737a3b78fa', 1, 1, "admin2@admin.com", "admin admin", NOW()),
  ('matias', 'dbc6bbe3b711e3f58f25638428fdfc12e34c8c38', 1, 1, "admin3@admin.com", "admin admin", NOW()),
  ('disabled', '07596f183f5e91b1778d5e47b2752b8d42aa763d', 0, 1, "admin4@admin.com", "admin admin", NOW()),
  ('grayfox', 'd8eafdf804a8d4f3894f1b10430f176418b251bf', 1, 1, "admin5@admin.com", "admin admin", NOW());


INSERT INTO `authorities` (`username`, `authority`)
VALUES
  ('admin', 'ROLE_ADMIN'),
  ('esteban', 'ROLE_USER'),
  ('matias', 'ROLE_USER'),
  ('disabled', 'ROLE_USER'),
  ('grayfox', 'ROLE_USER'),
  ('guido', 'ROLE_USER');