pipeline {
    agent any

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/your-repo.git',
                credentialsId: 'github-cred'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    bat 'mvn test'
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                allure([
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        failure {
            emailext(
                subject: "BUILD FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Build failed. Check: ${env.BUILD_URL}",
                to: "sprdmntester@gmail.com, devender.ripple@gmail.com"
            )
        }

        success {
            emailext(
                subject: "BUILD SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Build success. Check: ${env.BUILD_URL}",
                to: "sprdmntester@gmail.com, devender.ripple@gmail.com"
            )
        }
    }
}