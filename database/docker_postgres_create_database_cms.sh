#!/bin/bash
docker cp create_database_cms.sql postgres-cms:/
docker exec -it postgres-cms psql -U postgres -a -f create_database_cms.sql
docker exec -it postgres-cms rm create_database_cms.sql