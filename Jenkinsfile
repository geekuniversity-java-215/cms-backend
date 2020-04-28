pipeline {
    agent { docker { image 'maven:3.6.3-jdk-8' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}