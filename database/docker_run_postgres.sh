PGDATA_CMS=${HOME}/pgdata/cms
mkdir -p $PGDATA_CMS

docker run -d \
--name postgres-cms \
-e POSTGRES_PASSWORD=gjhUY876787ytuh87gdf \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v $PGDATA_CMS:/var/lib/postgresql/data \
-p 5442:5432 \
postgres
