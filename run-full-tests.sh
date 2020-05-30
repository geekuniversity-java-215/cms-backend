#!/bin/bash

source ztests/scripts/0-config_params.sh

# run docker postgres
./infrastructure/database/docker_run_cms-postgres.sh

{
# unit/integration tests will run in H2
ztests/scripts/1-unit-tests.sh && \

# disable H2 database
unset H2PARAMS && \

# system tests will run in docker postgres
ztests/scripts/2-system-tests.sh && \
sl -e;
}

docker container stop cms-postgres
docker container rm cms-postgres                                                         
