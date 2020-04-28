pipeline {
    agent { docker { image 'maven:3.6.3-jdk-8' } }
    stages {
        stage('build') {
            steps {
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