#!/bin/sh

# cmsapp
fromPath=cms-backend/cmsapp/src/main/resources/
FROM=${fromPath}application.properties
TO=${fromPath}application-dev.properties

cp -an $FROM $TO

if [[ ! -f "$TO" ]]; then
    sed -i 's/logback-spring.xml/logback-spring-dev.xml/g' $TO
fi



# core
fromPath=cms-backend/core/src/main/resources/
cp -an ${fromPath}core.properties ${fromPath}core-dev.properties



# mail
fromPath=cms-backend/mail/src/main/resources/
FROM=${fromPath}mail.properties
TO=${fromPath}mail-dev.properties

if [[ ! -f "$FROM" ]]; then

    echo -e "#mail settings\n" >> $TO
    echo -e "mail.username=vasya@pypkin.com\n" >> $TO
    echo -e "mail.password=vasyapassword\n" >> $TO

    cp -an $FROM $TO
fi




# payment
fromPath=cms-backend/payment/src/main/resources/
#cp -an ${fromPath}payment.properties ${fromPath}payment-dev.properties



# utils
fromPath=cms-backend/utils/src/main/resources/
FROM=${fromPath}logback-spring.xml
TO=${fromPath}logback-spring-dev.xml

if [[ ! -f "$TO" ]]; then

    cp -an $FROM $TO
    sed -i 's/<logger name=\"org.hibernate.SQL\" level=\"OFF\"\/>/<logger name=\"org.hibernate.SQL\" level=\"TRACE\"\/>/g' $TO
    
fi





