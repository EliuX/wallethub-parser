create database if not exists wallethub;

CREATE TABLE IF NOT EXISTS wallethub.requestlog (
  `id` SERIAL UNIQUE NOT NULL PRIMARY KEY,
  `ip` VARCHAR(15) NULL,
  `date` TIMESTAMP NOT NULL,
  CONSTRAINT UC_IP_DATE UNIQUE (`ip`,`date`)
);
