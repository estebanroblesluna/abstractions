USE `actions`;

INSERT INTO `actions`.`server_group` (`server_group_id`, `name`, `environment`, `team_id`) VALUES ('1', 'Local', 'DEV', '1');
INSERT INTO `actions`.`server` (`server_id`, `name`, `ipDNS`, `port`, `server_group_id`, `server_key`) VALUES ('1', 'Local', '127.0.0.1', '9876', '1', '1');
