USE `actions`;

ALTER TABLE  `application_snapshot`
ADD `environment` tinyblob NOT NULL,
ADD `zip`	longblob NOT NULL;
