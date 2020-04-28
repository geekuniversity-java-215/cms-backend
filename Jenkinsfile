pipeline {
    agent { docker { image 'maven:3.6.3-jdk-8' } }
    stages {

        // madness
        stage('dependencies') {
            steps {
                sh 'mkdir dependencies'
                sh 'cd dependencies/'
                sh 'git clone https://github.com/dreamworkerln/utils.git'
                sh 'cd utils/'
                sh 'mvn install'
                sh 'cd ~'
            }
        }

        // madness
        stage('dependencies2') {
            steps {
                sh 'wget http://ya.ru'
            }
        }
        stage('build') {
            steps {
                sh './install-properties.sh'
                sh 'mvn compile'
            }
        }
        stage('test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}