pipeline {
    agent any

    // Run job automatically every day at 5 AM
    triggers {
        cron('0 5 * * *')
    }

    environment {
        EMAIL_LIST = "sprdmntester@gmail.com,devender.ripple@gmail.com,chakravarthi@ripplemetering.com,preethi@ripplemetering.com"
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo "Pulling code from GitHub..."
                git url: 'https://github.com/your-repository-url.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                echo "Building the project..."
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo "Running Test Cases..."
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    bat 'mvn test'
                }
            }
        }

        stage('Generate Report') {
            steps {
                echo "Generating Test Report..."
                bat 'mvn surefire-report:report'
            }
        }

    }

    post {

        success {
            emailext(
                subject: "✅ BUILD SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                body: """
                <h2 style="color:green;">Build Successful</h2>

                <b>Job Name:</b> ${env.JOB_NAME}<br>
                <b>Build Number:</b> ${env.BUILD_NUMBER}<br>
                <b>Status:</b> ${currentBuild.currentResult}<br><br>

                <b>Build URL:</b><br>
                <a href="${env.BUILD_URL}">${env.BUILD_URL}</a><br><br>

                Regards,<br>
                Chakravarthi G
                """,
                to: "${EMAIL_LIST}",
                attachLog: true
            )
        }

        failure {
            emailext(
                subject: "❌ BUILD FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                body: """
                <h2 style="color:red;">Build Failed</h2>

                <b>Job Name:</b> ${env.JOB_NAME}<br>
                <b>Build Number:</b> ${env.BUILD_NUMBER}<br>
                <b>Status:</b> ${currentBuild.currentResult}<br><br>

                <b>Build URL:</b><br>
                <a href="${env.BUILD_URL}">${env.BUILD_URL}</a><br><br>

                Please check the logs and fix the issue.<br><br>

                Regards,<br>
                Chakravarthi G
                """,
                to: "${EMAIL_LIST}",
                attachLog: true
            )
        }

        always {
            echo "Pipeline execution finished."
        }
    }
}