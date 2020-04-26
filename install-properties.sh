#!/bin/bash

LIB_MODULES=(core mail payment utils)

# Prepare logging config fom library modules ------------------------

# cp application.properties application-dev.properties in <module>/test/main/resources/
# inside replace logback-spring.xml to logback-spring-dev.xml
create_tests_dev () {
    fromPath=$1/src/test/resources/
    FROM=${fromPath}application.properties
    TO=${fromPath}application-dev.properties

    if [[ ! -f "$TO" ]]; then
        cp -an $FROM $TO
        sed -i 's/logback-spring.xml/logback-spring-dev.xml/g' $TO
    fi
}

# execute it for all library modules
for var in "${LIB_MODULES[@]}"
do
  create_tests_dev ${var}
done


# cmsapp -----------------------------------------------------------
fromPath=cmsapp/src/main/resources/
FROM=${fromPath}application.properties
TO=${fromPath}application-dev.properties

if [[ ! -f "$TO" ]]; then
    cp -an $FROM $TO
    sed -i 's/logback-spring.xml/logback-spring-dev.xml/g' $TO
fi

# core -----------------------------------------------------------
fromPath=core/src/main/resources/
FROM=${fromPath}core.properties
TO=${fromPath}core-dev.properties
cp -an $FROM $TO


# configuration -----------------------------------------------------------
fromPath=configuration/src/main/resources/
FROM=${fromPath}logback-spring.xml
TO=${fromPath}logback-spring-dev.xml
if [[ ! -f "$TO" ]]; then

    cp -an $FROM $TO
    sed -i 's/<logger name=\"org.hibernate.SQL\" level=\"OFF\"\/>/<logger name=\"org.hibernate.SQL\" level=\"TRACE\"\/>/g' $TO
    sed -i 's/<logger name=\"com.github.geekuniversity_java_215.cmsbackend\" level=\"INFO\"\/>/<logger name=\"com.github.geekuniversity_java_215.cmsbackend\" level=\"TRACE\"\/>/g' $TO
fi

# mail -----------------------------------------------------------
fromPath=mail/src/main/resources/
FROM=${fromPath}mail.properties
TO=${fromPath}mail-dev.properties

if [[ ! -f "$FROM" ]]; then

    echo -e "#mail settings" >> $FROM
    echo -e "mail.username=vasya@pypkin.com" >> $FROM
    echo -e "mail.password=vasyapassword" >> $FROM

    cp -an $FROM $TO
fi




# payment -----------------------------------------------------------
fromPath=payment/src/main/resources/
 # NOT IMPLEMENTED






