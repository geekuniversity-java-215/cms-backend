#!/bin/bash

# bash escape tool
# http://dwaves.de/tools/escape/

##########################################################################################
# Copy application.properties to application-dev.properties
# in application and library modules
##########################################################################################

# lib modules
LIB_MODULES=(core/core-controllers core/core-services mail payment utils jrpc-client geodata)

# application modules
APPLICATION_MODULES=(auth-server cmsapp chat)

# all modules
ALL_MODULES=("${LIB_MODULES[@]}" "${APPLICATION_MODULES[@]}")

# Lib modules shouldn't contain @MultimoduleSpringBootApplication (except in tests)
# (as a @SpringBootApplication, @EnableGlobalMethodSecurity)



# cp application.properties application-dev.properties in <$1-module>/src/(<$2-test\main>)/resources/
# inside replace logback-spring.xml to logback-spring-dev.xml
create_application_dev () {
    fromPath=$1/src/$2/resources/
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
  create_application_dev ${var} test
done


# execute it for all application modules
for var in "${APPLICATION_MODULES[@]}"
do
  create_application_dev ${var} main
done




##########################################################################################
# Cook modules special <module_name>.properties
# located in <module_name>/src/main/resources/
##########################################################################################

# configuration ------------------------------------------------------------
# logback-spring.xml
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

    echo -e "mail.username=vasya@pypkin.com" >> $FROM
    echo -e "mail.password=vasyapassword" >> $FROM
    echo -e "mail.host=smtp.gmail.com" >> $FROM
    echo -e "mail.port=587" >> $FROM
    echo -e "mail.transport.protocol=smtp" >> $FROM
    echo -e "mail.smtp.auth=true" >> $FROM
    echo -e "mail.smtp.starttls.enable=true" >> $FROM

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


##########################################################################################
# Copy <module_name>.properties to <module_name>-dev.properties in all modules
# located in <module_name>/src/main/resources/
##########################################################################################

# copy <module-name>.properties to <module-name>-dev.properties
create_custom_dev () {
    fromPath=$1/src/main/resources/
    FROM=${fromPath}$1.properties
    TO=${fromPath}$1-dev.properties
    cp -an $FROM $TO 2>/dev/null || :
}


for var in "${ALL_MODULES[@]}"
do
  create_custom_dev ${var}
done
