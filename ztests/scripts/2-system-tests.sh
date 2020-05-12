#!/bin/bash

# subsystem tests
mvn -DskipTests package

# cms-app
java -jar cmsapp/target/cms-app-0.1.jar $PARAMS & PID1=$(echo $!)
sleep 3;

# auth-server
java -jar auth-server/target/auth-server-0.1.jar $PARAMS & PID2=$(echo $!)
sleep 7;

# check that process is running
kill -0 $PID1
kill -0 $PID2

# process system tests
mvn test -am -pl ztests/system-test -DskipTests=false $PARAMS

# kill applications
kill -9 $PID1
kill -9 $PID2
