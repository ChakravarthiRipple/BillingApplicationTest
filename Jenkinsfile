pipeline {
    agent any

    stages {
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
    }

    post {
        failure {
            emailext(
                subject: "❌ BUILD FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
Hi Team,

The Jenkins build has FAILED.

Job Name   : ${env.JOB_NAME}
Build No   : ${env.BUILD_NUMBER}
Status     : ${currentBuild.currentResult}

Build URL:
${env.BUILD_URL}

Please check the logs.

Regards,
Chakravarthi G
""",
                to: "sprdmntester@gmail.com, devender.ripple@gmail.com, chakravarthi@ripplemetering.com, preethi@ripplemetering.com"
            )
        }

        success {
            emailext(
                subject: "✅ BUILD SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
Hi Team,

The Jenkins build was SUCCESSFUL.

Job Name   : ${env.JOB_NAME}
Build No   : ${env.BUILD_NUMBER}

Build URL:
${env.BUILD_URL}

Regards,
Chakravarthi G
""",
                to: "sprdmntester@gmail.com, devender.ripple@gmail.com, chakravarthi@ripplemetering.com, preethi@ripplemetering.com"
            )
        }
    }
}