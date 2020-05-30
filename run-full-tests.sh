#!/bin/bash

source ztests/scripts/0-config_params.sh

# run docker postgres
./infrastructure/database/docker_run_cms-postgres.sh

{
ztests/scripts/1-unit-tests.sh && \            # unit/integration tests will run in H2
unset H2PARAMS && \
ztests/scripts/2-system-tests.sh && \          # system tests will run in docker postgres
sl -e;
} ||
docker container stop cms-postgres; docker container rm cms-postgres
