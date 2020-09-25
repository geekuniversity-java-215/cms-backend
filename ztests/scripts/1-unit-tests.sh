#!/bin/bash
mvn ${POSTGRESQL_PARAMS} clean test package
exit $?