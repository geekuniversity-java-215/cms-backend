#!/bin/bash

# bash escape tool
# http://dwaves.de/tools/escape/

# Prepare dev src/test resources in library modules ----------------------------

LIB_MODULES=(core/core-controllers core/core-services mail payment utils jrpc-client geodata)

# This modules shouldn't contain @SpringBootApplication (except in tests)

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





# Prepare dev src/main resources  in modules ----------------------------

# core ------------------------------------------------------------------

# core-controllers
fromPath=core/core-controllers/src/main/resources/
FROM=${fromPath}core-controllers.properties
TO=${fromPath}core-controllers-dev.properties
cp -an $FROM $TO

# core-services
fromPath=core/core-services/src/main/resources/
FROM=${fromPath}core-services.properties
TO=${fromPath}core-services-dev.properties
cp -an $FROM $TO

# core-services logback-spring.xml
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
    echo -e "mail.host=smtp.gmail.com" >> $FROM
    echo -e "mail.port=587" >> $FROM
    echo -e "mail.username=vasya@pypkin.com" >> $FROM
    echo -e "mail.password=vasyapassword" >> $FROM
    
    cp -an $FROM $TO

    # copy mail*.properties from folder above (if exists, suppress the error exit code and message)
    # to not manually copy mail*.properties
    cp ../cms-backend-properties/mail/mail*.properties mail/src/main/resources/ 2>/dev/null || :
fi





# payment -----------------------------------------------------------
fromPath=payment/src/main/resources/
FROM=${fromPath}payment.properties
TO=${fromPath}payment-dev.properties

if [[ ! -f "$FROM" ]]; then

    echo -e "paypal.clientId=AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" >> $FROM
    echo -e "paypal.clientSecret=BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB" >> $FROM
    echo -e "paypal.mode=sandbox" >> $FROM
    cp -an $FROM $TO

    # copy payment*.properties from folder above (if exists, suppress the error exit code and message)
    # to not manually copy payment*.properties
    cp ../cms-backend-properties/payment/payment*.properties payment/src/main/resources/ 2>/dev/null || :
fi


# jrpc-client ---------------------------------------------------------
fromPath=jrpc-client/src/main/resources/
FROM=${fromPath}jrpc-client.properties
TO=${fromPath}jrpc-client-dev.properties
cp -an $FROM $TO


# geodata -----------------------------------------------------------
fromPath=geodata/src/main/resources/
FROM=${fromPath}geodata.properties
TO=${fromPath}geodata-dev.properties
cp -an $FROM $TO










#####################################################################
########################### APPLICATIONS ############################
#####################################################################

# cp application.properties to application-dev.properties

# cmsapp ------------------------------------------------------------
fromPath=cmsapp/src/main/resources/
FROM=${fromPath}application.properties
TO=${fromPath}application-dev.properties

if [[ ! -f "$TO" ]]; then
    cp -an $FROM $TO
    sed -i 's/logback-spring.xml/logback-spring-dev.xml/g' $TO
fi


# auth-server -----------------------------------------------------------
fromPath=auth-server/src/main/resources/
FROM=${fromPath}application.properties
TO=${fromPath}application-dev.properties

if [[ ! -f "$TO" ]]; then
    cp -an $FROM $TO
    sed -i 's/logback-spring.xml/logback-spring-dev.xml/g' $TO
fi



# chat -----------------------------------------------------------
fromPath=chat/src/main/resources/
FROM=${fromPath}application.properties
TO=${fromPath}application-dev.properties

if [[ ! -f "$TO" ]]; then
    cp -an $FROM $TO
    sed -i 's/logback-spring.xml/logback-spring-dev.xml/g' $TO
fi








