#!/bin/bash

mvn ${H2_PARAMS} clean package test
exit $?