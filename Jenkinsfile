pipeline {
        agent {
            docker {
                //image 'maven:3.6.3-jdk-8'
                image 'dreamworkerln/cms-mvn'
                //args '-v $HOME/.m2:/root/.m2:z -u root -v /root/projects/cms-backend/build:$HOME/out:z -u root'
                //args '-v $HOME/.m2:/root/.m2:z -u root -v /root/projects/cms-backend/build:$HOME/out:z -u root --network="host"'
                args '-v /var/run/docker.sock:/var/run/docker.sock -v $HOME/.m2:/root/.m2:z -u root -v /root/projects/cms-backend/build:$HOME/out:z -u root --network="host"'
                //args '-v /var/run/docker.sock:/var/run/docker.sock ... --network="host" -u jenkins:docker'

                reuseNode true
            }
        }

    environment {
        MAIL_URL=credentials('mail_url')
        PAYMENT_URL=credentials('payment_url')
    }
        
    stages {

        stage('purge cms') {
            steps {
                //sh 'echo "31.210.208.189:5442:cms:cmsadmin:cmsadminpassword" > ~/.pgpass'
                //sh 'chmod go-rwx ~/.pgpass'
                //sh 'PGOPTIONS=--search_path=cms psql -h 31.210.208.189 -p 5442 -U cmsadmin --dbname=cms -f infrastructure/database/purge_schema.sql'
                sh 'PGOPTIONS=--search_path=cms psql postgresql://cmsadmin:cmsadminpassword@31.210.208.189:5442/cms -f infrastructure/database/purge_schema.sql'
            }
        }

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
                sh 'mvn -U -DskipTests clean package'

                sh '''
                    mkdir -p $HOME/out/auth-server
                    mkdir -p $HOME/out/cmsapp
                    cp auth-server/target/*.jar $HOME/out/auth-server/
                    cp cmsapp/target/*.jar $HOME/out/cmsapp/
                '''
            }
        }

        stage('tests') {
            steps {
                sh '''
                    . ./ztests/scripts/0-config_params
                    echo $H2PARAMS
                    ./ztests/scripts/1-unit-tests.sh
                '''
            }
        }

        stage('system tests') {
            steps {

                sh '''
                    . ./ztests/scripts/0-config_params
                    POSTGRESQL_PARAMS=$POSTGRESQL_EXTERNAL_PARAMS
                    echo $POSTGRESQL_PARAMS
                    ./ztests/scripts/2-system-tests.sh
                '''
            }
        }
    }
//     post {
//         always {
//             echo 'One way or another, I have finished'
//             // stop container
//         }
//     }
}