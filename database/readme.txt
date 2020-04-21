1. Создать базу на локальном postgres:

postgres_local_create_database_cms.sh 




2. Запустить postgres из docker, создать базу:

docker_run_postgres.sh
docker_postgres_create_database_cms.sh

При этом файлы базы cms разместятся в ${HOME}/pgdata/cms/
а порт postgres будет 5442 

