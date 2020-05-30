#!/bin/bash

# auth-server
java ${POSTGRESQL_PARAMS} -jar auth-server/target/auth-server-0.1.jar & PID1=$(echo $!)
# cms-app
java ${POSTGRESQL_PARAMS} -jar cmsapp/target/cms-app-0.1.jar & PID2=$(echo $!)


# subsystem tests
mvn -DskipTests package

sleep 2;
# check that process is running
kill -0 $PID1
kill -0 $PID2

# process system tests
mvn ${POSTGRESQL_PARAMS} test -am -pl ztests/system-test -DskipTests=false
RESULT_CODE=$?


# kill all applications
kill -9 ${PID1}
kill -9 ${PID2}

exit ${RESULT_CODE}
