#!/bin/bash

#mvn -DskipTests package

# auth-server
java ${POSTGRESQL_PARAMS} -jar auth-server/target/auth-server-0.1.jar & PID1=$(echo $!)
sleep 3;

# cms-app
java ${POSTGRESQL_PARAMS} -jar cmsapp/target/cms-app-0.1.jar & PID2=$(echo $!)
sleep 6;

# check that process is running
kill -0 $PID1
kill -0 $PID2

# -am, --also-make: If project list is specified, also build projects required by the list
# -pl, --projects:  Comma-delimited list of specified reactor projects to build instead of all projects.

# process system tests
mvn ${POSTGRESQL_PARAMS} test -am -pl ztests/system-test -DskipTests=false
RESULT_CODE=$?



# kill all applications
kill -9 ${PID1}
kill -9 ${PID2}

exit ${RESULT_CODE}
