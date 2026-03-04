pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Code checked out successfully'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }

    post {
        always {
            emailext(
                subject: "Build ${currentBuild.currentResult}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
Hi Team,

Build Status: ${currentBuild.currentResult}

Job Name   : ${env.JOB_NAME}
Build No   : ${env.BUILD_NUMBER}
Build URL  : ${env.BUILD_URL}

Regards,
Jenkins
""",
                to: "devender.ripple@gmail.com, chakravarthi@ripplemetering.com"
            )
        }
    }
}