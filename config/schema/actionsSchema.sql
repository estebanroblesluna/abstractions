USE `actions`;

DROP TABLE IF EXISTS `diagrams`;

CREATE TABLE diagrams (
    diagram_id varchar(50) NOT NULL,
    jsonRepresentation TEXT,
    primary key (diagram_id)
);