#!/bin/bash

# Prepare dev src/test resources  in modules ----------------------------
LIB_MODULES=(core/core-controllers core/core-services mail payment utils)

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
 # NOT IMPLEMENTED



########################### APPLICATIONS ###########################



# cmsapp -----------------------------------------------------------
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








