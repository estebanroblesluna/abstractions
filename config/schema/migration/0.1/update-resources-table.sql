USE `actions`;

ALTER TABLE  `resource`
ADD `is_snapshot` BIT,
ADD `application_snapshot_id` bigint(20) DEFAULT NULL;

