1. Создать базу на локальном postgres:

postgres_local_create_database_cms.sh 




2. Запустить postgres из docker(база создается автоматически при первом запуске):

docker_run_cms_postgres.sh

При этом файлы базы cms разместятся в ${HOME}/pgdata/cms/
а порт postgres будет 5442

Проверить: 
psql -h localhost -p 5442 -U cmsadmin cms
