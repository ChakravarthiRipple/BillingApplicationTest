pipeline {
    agent any

    triggers {
        cron('0 6 * * *')
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Code checked out successfully'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile -q'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test -Dheadless=true'
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        always {
            echo "Build result: ${currentBuild.currentResult}"
            archiveArtifacts artifacts: 'allure-results/**',
                             allowEmptyArchive: true
        }

        success {
            emailext (
                subject: "BUILD PASSED - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                to: "sprdmntester@gmail.com,devender.ripple@gmail.com,chakravarthi@ripplemetering.com,preethi@ripplemetering.com",
                body: """
                <html>
                <body style="font-family:Arial,sans-serif; background:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background:white;
                            border-radius:10px; overflow:hidden;
                            box-shadow:0 2px 10px rgba(0,0,0,0.1);">
                    <div style="background:#00c853; padding:24px; text-align:center;">
                        <h1 style="color:white; margin:0;">BUILD PASSED</h1>
                        <p style="color:#e8f5e9; margin:8px 0 0;">Ripple Metering Automation</p>
                    </div>
                    <div style="padding:24px;">
                        <p>Hi Team,</p>
                        <p>All tests have <b style="color:#00c853;">PASSED</b>.</p>
                        <table style="width:100%; border-collapse:collapse; margin:16px 0;">
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee; font-weight:bold; width:35%;">Job</td>
                                <td style="padding:12px; border:1px solid #eee;">${env.JOB_NAME}</td>
                            </tr>
                            <tr>
                                <td style="padding:12px; border:1px solid #eee; font-weight:bold;">Build</td>
                                <td style="padding:12px; border:1px solid #eee;">#${env.BUILD_NUMBER}</td>
                            </tr>
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee; font-weight:bold;">Status</td>
                                <td style="padding:12px; border:1px solid #eee;">
                                    <span style="background:#00c853; color:white;
                                                 padding:4px 12px; border-radius:20px;">PASSED</span>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:12px; border:1px solid #eee; font-weight:bold;">Duration</td>
                                <td style="padding:12px; border:1px solid #eee;">${currentBuild.durationString}</td>
                            </tr>
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee; font-weight:bold;">Build URL</td>
                                <td style="padding:12px; border:1px solid #eee;">
                                    <a href="${env.BUILD_URL}" style="color:#1976d2;">Click here</a>
                                </td>
                            </tr>
                        </table>
                        <div style="text-align:center; margin-top:20px;">
                            <a href="${env.BUILD_URL}"
                               style="background:#00c853; color:white; padding:12px 32px;
                                      text-decoration:none; border-radius:6px; font-weight:bold;">
                                View Build Details
                            </a>
                        </div>
                    </div>
                    <div style="background:#f9f9f9; padding:16px; text-align:center; border-top:1px solid #eee;">
                        <p style="color:#888; font-size:12px; margin:0;">
                            Jenkins CI/CD | Ripple Metering Automation | Regards, Chakravarthi G
                        </p>
                    </div>
                </div>
                </body></html>
                """
            )
        }

        failure {
            emailext (
                subject: "BUILD FAILED - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                attachLog: true,
                to: "sprdmntester@gmail.com,devender.ripple@gmail.com,chakravarthi@ripplemetering.com,preethi@ripplemetering.com",
                body: """
                <html>
                <body style="font-family:Arial,sans-serif; background:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background:white;
                            border-radius:10px; overflow:hidden;
                            box-shadow:0 2px 10px rgba(0,0,0,0.1);">
                    <div style="background:#e53935; padding:24px; text-align:center;">
                        <h1 style="color:white; margin:0;">BUILD FAILED</h1>
                        <p style="color:#ffebee; margin:8px 0 0;">Ripple Metering Automation</p>
                    </div>
                    <div style="padding:24px;">
                        <p>Hi Team,</p>
                        <p>The build has <b style="color:#e53935;">FAILED</b>. Please check the attached log.</p>
                        <table style="width:100%; border-collapse:collapse; margin:16px 0;">
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee; font-weight:bold; width:35%;">Job</td>
                                <td style="padding:12px; border:1px solid #eee;">${env.JOB_NAME}</td>
                            </tr>
                            <tr>
                                <td style="padding:12px; border:1px solid #eee; font-weight:bold;">Build</td>
                                <td style="padding:12px; border:1px solid #eee;">#${env.BUILD_NUMBER}</td>
                            </tr>
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee; font-weight:bold;">Status</td>
                                <td style="padding:12px; border:1px solid #eee;">
                                    <span style="background:#e53935; color:white;
                                                 padding:4px 12px; border-radius:20px;">FAILED</span>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:12px; border:1px solid #eee; font-weight:bold;">Duration</td>
                                <td style="padding:12px; border:1px solid #eee;">${currentBuild.durationString}</td>
                            </tr>
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee; font-weight:bold;">Build URL</td>
                                <td style="padding:12px; border:1px solid #eee;">
                                    <a href="${env.BUILD_URL}" style="color:#1976d2;">Click here</a>
                                </td>
                            </tr>
                        </table>
                        <div style="background:#fff3e0; border-left:4px solid #ff9800;
                                    padding:12px; margin:16px 0; border-radius:0 6px 6px 0;">
                            <p style="margin:0; color:#e65100; font-size:13px;">
                                Build log is attached. Please review and fix failing tests.
                            </p>
                        </div>
                        <div style="text-align:center; margin-top:20px;">
                            <a href="${env.BUILD_URL}"
                               style="background:#e53935; color:white; padding:12px 32px;
                                      text-decoration:none; border-radius:6px; font-weight:bold;">
                                View Failure Details
                            </a>
                        </div>
                    </div>
                    <div style="background:#f9f9f9; padding:16px; text-align:center; border-top:1px solid #eee;">
                        <p style="color:#888; font-size:12px; margin:0;">
                            Jenkins CI/CD | Ripple Metering Automation | Regards, Chakravarthi G
                        </p>
                    </div>
                </div>
                </body></html>
                """
            )
        }

        cleanup {
            cleanWs()
        }
    }
}