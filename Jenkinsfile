pipeline {
    agent { docker { image 'maven:3.6.3-jdk-8' } }
    stages {

        // madness
        stage('dependencies') {
            steps {
                sh '''
                ls -lah
                mkdir -p dependencies
                cd dependencies/
                if [ ! -d "dependencies" ] ; then
                    git clone https://github.com/dreamworkerln/utils.git
                fi
                cd utils/
                mvn install
                '''
            }
        }

//         stage('dependencies2') {
//             steps {
//                 sh 'wget http://ya.ru'
//             }
//         }

        stage('build') {
            steps {
                sh './install-properties.sh'
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