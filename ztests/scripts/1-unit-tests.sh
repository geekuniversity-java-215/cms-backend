#!/bin/bash

mvn ${H2_PARAMS} clean test
exit $?