--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: src/main/resources/dbchangelog.xml
--  Ran at: 4/15/21, 3:51 PM
--  Against: root@localhost@jdbc:mysql://localhost:3306/writers_info
--  Liquibase version: 4.3.2
--  *********************************************************************

--  Lock Database
UPDATE DATABASECHANGELOGLOCK SET `LOCKED` = 1, LOCKEDBY = 'PSB133S01ZFP (192.168.0.16)', LOCKGRANTED = '2021-04-15 15:51:08.483' WHERE ID = 1 AND `LOCKED` = 0;

--  Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET `LOCKED` = 0, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

