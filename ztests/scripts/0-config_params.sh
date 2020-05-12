#!/bin/bash

export H2PARAMS="-Dspring.datasource.url=jdbc:h2:mem:testdb -Dspring.datasource.driverClassName=org.h2.Driver -Dspring.datasource.username=sa -Dspring.datasource.password=password -Dspring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
