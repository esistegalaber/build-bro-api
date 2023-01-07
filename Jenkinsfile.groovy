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

        stage('Boot Db') {
            steps {
                script {
                    sh "docker run --name build_bruh --network=build_bro_net -d -p 3306:3306 -e MYSQL_DATABASE=build_bruh -e MYSQL_USER=build_bruh -e MYSQL_PASSWORD=build_bruh -e MYSQL_ROOT_PASSWORD=build_bruh mariadb:10.10"
                }
            }
        }
        stage('Build Api') {
            steps {
                script {
                    sh "./gradlew clean build"
                }
            }
        }
    }
}