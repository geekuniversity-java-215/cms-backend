#!/usr/bin/env bash


#sudo -u postgres PGOPTIONS=--search_path=cms psql --dbname=cms -f infrastructure/database/purge_schema.sql

#echo "localhost:5442:cms:cmsadmin:cmsadminpassword" > ~/.pgpass
#echo "localhost:5432:cms:cmsadmin:cmsadminpassword" >> ~/.pgpass
#chmod go-rwx ~/.pgpass

echo "port 5442:"
PGOPTIONS=--search_path=cms psql postgresql://cmsadmin:cmsadminpassword@localhost:5442/cms -f infrastructure/database/purge_schema.sql
echo "port 5432:"
PGOPTIONS=--search_path=cms psql postgresql://cmsadmin:cmsadminpassword@localhost:5432/cms -f infrastructure/database/purge_schema.sql

#-h localhost -p 5442 -U cmsadmin --dbname=cms