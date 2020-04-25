#!/bin/bash

# cmsapp
fromPath=cmsapp/src/main/resources/
FROM=${fromPath}application.properties
TO=${fromPath}application-dev.properties

if [ ! -f "$TO" ]; then
    cp -an $FROM $TO
    sed -i 's/logback-spring.xml/logback-spring-dev.xml/g' $TO
fi



# core
fromPath=core/src/main/resources/
FROM=${fromPath}core.properties
TO=${fromPath}core-dev.properties

cp -an $FROM $TO



# mail
fromPath=mail/src/main/resources/
FROM=${fromPath}mail.properties
TO=${fromPath}mail-dev.properties

if [ ! -f "$FROM" ]; then

    echo -e "#mail settings" >> $FROM
    echo -e "mail.username=vasya@pypkin.com" >> $FROM
    echo -e "mail.password=vasyapassword" >> $FROM

    cp -an $FROM $TO
fi




# payment
fromPath=payment/src/main/resources/
#cp -an ${fromPath}payment.properties ${fromPath}payment-dev.properties



# utils
fromPath=utils/src/main/resources/
FROM=${fromPath}logback-spring.xml
TO=${fromPath}logback-spring-dev.xml

if [ ! -f "$TO" ]; then

    cp -an $FROM $TO
    sed -i 's/<logger name=\"org.hibernate.SQL\" level=\"OFF\"\/>/<logger name=\"org.hibernate.SQL\" level=\"TRACE\"\/>/g' $TO
fi

