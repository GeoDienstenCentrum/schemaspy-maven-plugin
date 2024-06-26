ALTER SESSION SET "_ORACLE_SCRIPT"=true;

-- USER SQL
CREATE USER "SCHEMASPY" IDENTIFIED BY "schemaspy"  DEFAULT TABLESPACE "USERS" TEMPORARY TABLESPACE "TEMP";

-- QUOTAS
ALTER USER "SCHEMASPY" QUOTA UNLIMITED ON "USERS";

-- ROLES
GRANT "CONNECT" TO "SCHEMASPY" ;
GRANT "RESOURCE" TO "SCHEMASPY" ;
ALTER USER "SCHEMASPY" DEFAULT ROLE "CONNECT","RESOURCE";
GRANT CREATE VIEW TO "SCHEMASPY" ;
GRANT CREATE SYNONYM TO "SCHEMASPY" ;
