pipeline {

    options {
        buildDiscarder(logRotator(numToKeepStr: '50'))
        disableConcurrentBuilds()
    }

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
                script {
                    sh "git clean -dfx"
                }
            }
        }

        stage('Boot DB') {
            steps {
                script {
                    sh "setup_db.sh"
                }
            }
        }
    }
}