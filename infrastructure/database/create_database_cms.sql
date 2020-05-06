CREATE DATABASE cms;
CREATE USER cmsadmin WITH PASSWORD 'cmsadminpassword';
GRANT ALL PRIVILEGES ON DATABASE cms TO cmsadmin;
\c cms
CREATE SCHEMA cms AUTHORIZATION cmsadmin;
SET search_path TO cms;

