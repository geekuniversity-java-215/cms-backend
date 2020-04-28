pipeline {
    agent { docker { image 'maven:3.6.3-jdk-8' } }

    environment {
        MAIL_URL     = credentials('mail_url')
        AWS_SECRET_ACCESS_KEY = credentials('mail_password')
    }
        
    stages {

        // madness
        stage('dependencies') {
            steps {
                sh '''

                echo "$MAIL_URL"
                curl "$MAIL_URL"

                #ls -lah
                pwd
                cp ../
                mkdir -p dependencies
                cd dependencies/
                if [ ! -d "utils" ] ; then
                    git clone https://github.com/dreamworkerln/utils.git
                fi
                cd utils/
                mvn install
                '''
            }
        }

        stage('properties') {
            steps {
                sh 'git clean -fdx -e /dependencies'
                sh './install-properties.sh'

//                 withCredentials([usernameColonPassword(credentialsId: 'mysecret_mail', variable: 'URL')]) {
//                 sh '''
//                     wget "url"
//                 '''
//                 }
//
//                 withCredentials([usernameColonPassword(credentialsId: 'mysecret_mail', variable: 'password')]) {
//                 sh '''
//                     unzip
//                 '''
//                 }


                //
            }
        }

        stage('build') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('test') {
            steps {
                sh 'mvn test -Dspring.datasource.url=jdbc:h2:mem:testdb -Dspring.datasource.driverClassName=org.h2.Driver -Dspring.datasource.username=sa -Dspring.datasource.password=password -Dspring.jpa.database-platform=org.hibernate.dialect.H2Dialect'
            }
        }
    }
}