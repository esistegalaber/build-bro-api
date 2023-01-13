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
                    withEnv(["PATH+EXTRA=/usr/local/bin"]) {
                        sh "docker run --name build_bruh_db -d -p 3306:3306 -e MYSQL_DATABASE=build_bruh -e MYSQL_USER=build_bruh -e MYSQL_PASSWORD=build_bruh -e MYSQL_ROOT_PASSWORD=build_bruh mariadb:10.10"
                    }
                }
            }
        }
        stage('Build Api') {
            steps {
                script {
                    sh "./gradlew clean build jacocoTestReport"
                }
            }
        }
    }
    post('Cleanup') {
        always {
            script {
                withEnv(["PATH+EXTRA=/usr/local/bin"]) {
                    sh "docker stop build_bruh_db | true"
                    sh "docker rm build_bruh_db | true"
                }

            }
        }
    }
}