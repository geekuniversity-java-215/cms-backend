pipeline {
        agent {
            docker {
                image 'maven:3.6.3-jdk-8'
                args '-v $HOME/.m2:/root/.m2:z -u root'
                reuseNode true
            }
        }

    environment {
        MAIL_URL=credentials('mail_url')
        PAYMENT_URL=credentials('payment_url')
        H2PARAMS="-Dspring.datasource.url=jdbc:h2:mem:testdb -Dspring.datasource.driverClassName=org.h2.Driver -Dspring.datasource.username=sa -Dspring.datasource.password=password -Dspring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
    }
        
    stages {

        // madness
        stage('dependencies') {
            steps {

                sh '''
                #ls -lah
                pwd
                mkdir -p dependencies
                cd dependencies/
                if [ ! -d "utils" ] ; then
                    git clone https://github.com/dreamworkerln/utils.git
                fi
                cd utils/
                mvn clean install
                '''
            }
        }

        stage('properties') {
            steps {
                sh 'git clean -fdx -e /dependencies'
                sh './install-properties.sh'

                // mail credentials
                sh '''
                    set -x
                    pwd
                    wget "$MAIL_URL" -O mail.resources.zip
                    unzip -o mail.resources.zip -d mail/src/main/resources/
                    rm mail.resources.zip
                '''

                // payment credentials
                sh '''
                    set -x
                    pwd
                    wget "$PAYMENT_URL" -O payment.resources.zip
                    unzip -o payment.resources.zip -d payment/src/main/resources/
                    rm payment.resources.zip
                '''
            }
        }

        stage('build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('tests') {
            steps {
                sh '''
                    . ./ztests/scripts/0-config_params.sh
                    echo "==========================="
                    echo $H2PARAMS
                    echo "==========================="
                    ./ztests/scripts/1-unit-tests.sh
                '''
            }
        }

        stage('system tests') {
            steps {
                sh '''
                    . ./ztests/scripts/0-config_params.sh
                    ./ztests/scripts/2-system-tests.sh
                '''
            }
        }
    }
}