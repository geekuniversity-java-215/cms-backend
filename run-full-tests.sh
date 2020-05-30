#!/bin/bash

source ztests/scripts/0-config_params.sh

# run docker postgres
./ztests/scripts/docker_run_cms-postgres.sh

{
# unit/integration tests will run in H2
# ztests/scripts/1-unit-tests.sh && \

# system tests will run in docker postgres
ztests/scripts/2-system-tests.sh && \
sl -e;
}

echo 'kill container:'
docker container rm -f cms-postgres

