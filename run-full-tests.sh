#!/bin/bash

source ztests/scripts/99-java_home
source ztests/scripts/0-config_params

{
# unit/integration tests
echo -e "---===== UNIT TESTING =====---" && \
ztests/scripts/1-unit-tests.sh && \

# system tests
echo -e "---===== SYSTEM TESTING =====---" && \

echo 'run postgres container' && \
./ztests/scripts/docker_run_cms-postgres.sh && \
ztests/scripts/2-system-tests.sh && \
sl -e;
}
echo 'kill postgres container'
docker container rm -f cms-postgres



