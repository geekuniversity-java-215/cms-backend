pipeline {
    agent { docker { image 'maven:3.6.3-jdk-8' } }
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
                mvn install
                '''
                
                //sh 'pwd'
                sh 'git clean -fdx -e /dependencies'
            }
        }

        stage('properties') {
            steps {
                //sh 'wget http://ya.ru'
                sh './install-properties.sh'
            }
        }

        stage('build') {
            steps {
                sh 'mvn compile'
            }
        }

           // not working, need database
//         stage('test') {
//             steps {
//                 sh 'mvn test'
//             }
//         }
    }
}