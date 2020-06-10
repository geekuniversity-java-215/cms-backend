#!/usr/bin/env bash
#PGDATA_CMS=${HOME}/pgdata/cms
#mkdir -p $PGDATA_CMS

docker run -d \
--name cms-postgres \
-e POSTGRES_PASSWORD=gjhUY876787ytuh87gdf \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-p 5442:5432 \
dreamworkerln/cms-postgres
#-v $PGDATA_CMS:/var/lib/postgresql/data \
