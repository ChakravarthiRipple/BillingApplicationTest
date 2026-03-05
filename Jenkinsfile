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

        stage('Generate Allure Report') {
            steps {
                bat 'mvn allure:report'
            }
        }

        stage('Publish Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo "Build result: ${currentBuild.currentResult}"
            archiveArtifacts artifacts: 'allure-results/**,target/site/allure-maven-plugin/**',
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

                    <!-- Header -->
                    <div style="background:#00c853; padding:24px; text-align:center;">
                        <h1 style="color:white; margin:0; font-size:26px;">BUILD PASSED</h1>
                        <p style="color:#e8f5e9; margin:8px 0 0; font-size:14px;">
                            Ripple Metering Automation Framework
                        </p>
                    </div>

                    <!-- Body -->
                    <div style="padding:24px;">
                        <p style="color:#333; font-size:14px;">Hi Team,</p>
                        <p style="color:#333; font-size:14px;">
                            All automation tests have
                            <b style="color:#00c853;">PASSED</b> successfully.
                        </p>

                        <table style="width:100%; border-collapse:collapse; margin:16px 0;">
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; width:35%; color:#555; font-size:13px;">
                                    Job Name
                                </td>
                                <td style="padding:12px; border:1px solid #eee;
                                           color:#333; font-size:13px;">
                                    ${env.JOB_NAME}
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; color:#555; font-size:13px;">
                                    Build Number
                                </td>
                                <td style="padding:12px; border:1px solid #eee;
                                           color:#333; font-size:13px;">
                                    #${env.BUILD_NUMBER}
                                </td>
                            </tr>
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; color:#555; font-size:13px;">
                                    Status
                                </td>
                                <td style="padding:12px; border:1px solid #eee;">
                                    <span style="background:#00c853; color:white;
                                                 padding:4px 14px; border-radius:20px;
                                                 font-size:12px; font-weight:bold;">
                                        PASSED
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; color:#555; font-size:13px;">
                                    Duration
                                </td>
                                <td style="padding:12px; border:1px solid #eee;
                                           color:#333; font-size:13px;">
                                    ${currentBuild.durationString}
                                </td>
                            </tr>
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; color:#555; font-size:13px;">
                                    Build URL
                                </td>
                                <td style="padding:12px; border:1px solid #eee; font-size:13px;">
                                    <a href="${env.BUILD_URL}"
                                       style="color:#1976d2; text-decoration:none;">
                                        Click here to view
                                    </a>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; color:#555; font-size:13px;">
                                    Allure Report
                                </td>
                                <td style="padding:12px; border:1px solid #eee; font-size:13px;">
                                    <a href="${env.BUILD_URL}allure/"
                                       style="color:#1976d2; text-decoration:none;">
                                        View Allure Report
                                    </a>
                                </td>
                            </tr>
                        </table>

                        <!-- Allure Report Button -->
                        <div style="text-align:center; margin-top:24px;">
                            <a href="${env.BUILD_URL}"
                               style="background:#00c853; color:white;
                                      padding:12px 24px; text-decoration:none;
                                      border-radius:6px; font-weight:bold;
                                      font-size:14px; display:inline-block;
                                      margin-right:10px;">
                                View Build
                            </a>
                            <a href="${env.BUILD_URL}allure/"
                               style="background:#1976d2; color:white;
                                      padding:12px 24px; text-decoration:none;
                                      border-radius:6px; font-weight:bold;
                                      font-size:14px; display:inline-block;">
                                View Allure Report
                            </a>
                        </div>
                    </div>

                    <!-- Footer -->
                    <div style="background:#f9f9f9; padding:16px;
                                text-align:center; border-top:1px solid #eee;">
                        <p style="color:#888; font-size:12px; margin:0;">
                            Automated by Jenkins CI/CD Pipeline
                        </p>
                        <p style="color:#888; font-size:12px; margin:4px 0 0;">
                            Ripple Metering Automation Framework
                        </p>
                        <p style="color:#888; font-size:12px; margin:4px 0 0;">
                            Regards, Chakravarthi G
                        </p>
                    </div>

                </div>
                </body>
                </html>
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

                    <!-- Header -->
                    <div style="background:#e53935; padding:24px; text-align:center;">
                        <h1 style="color:white; margin:0; font-size:26px;">BUILD FAILED</h1>
                        <p style="color:#ffebee; margin:8px 0 0; font-size:14px;">
                            Ripple Metering Automation Framework
                        </p>
                    </div>

                    <!-- Body -->
                    <div style="padding:24px;">
                        <p style="color:#333; font-size:14px;">Hi Team,</p>
                        <p style="color:#333; font-size:14px;">
                            The automation build has
                            <b style="color:#e53935;">FAILED</b>.
                            Please review the attached log and fix the issues.
                        </p>

                        <table style="width:100%; border-collapse:collapse; margin:16px 0;">
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; width:35%; color:#555; font-size:13px;">
                                    Job Name
                                </td>
                                <td style="padding:12px; border:1px solid #eee;
                                           color:#333; font-size:13px;">
                                    ${env.JOB_NAME}
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; color:#555; font-size:13px;">
                                    Build Number
                                </td>
                                <td style="padding:12px; border:1px solid #eee;
                                           color:#333; font-size:13px;">
                                    #${env.BUILD_NUMBER}
                                </td>
                            </tr>
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; color:#555; font-size:13px;">
                                    Status
                                </td>
                                <td style="padding:12px; border:1px solid #eee;">
                                    <span style="background:#e53935; color:white;
                                                 padding:4px 14px; border-radius:20px;
                                                 font-size:12px; font-weight:bold;">
                                        FAILED
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; color:#555; font-size:13px;">
                                    Duration
                                </td>
                                <td style="padding:12px; border:1px solid #eee;
                                           color:#333; font-size:13px;">
                                    ${currentBuild.durationString}
                                </td>
                            </tr>
                            <tr style="background:#f9f9f9;">
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; color:#555; font-size:13px;">
                                    Build URL
                                </td>
                                <td style="padding:12px; border:1px solid #eee; font-size:13px;">
                                    <a href="${env.BUILD_URL}"
                                       style="color:#1976d2; text-decoration:none;">
                                        Click here to view
                                    </a>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:12px; border:1px solid #eee;
                                           font-weight:bold; color:#555; font-size:13px;">
                                    Allure Report
                                </td>
                                <td style="padding:12px; border:1px solid #eee; font-size:13px;">
                                    <a href="${env.BUILD_URL}allure/"
                                       style="color:#1976d2; text-decoration:none;">
                                        View Allure Report
                                    </a>
                                </td>
                            </tr>
                        </table>

                        <div style="background:#fff3e0; border-left:4px solid #ff9800;
                                    padding:12px 16px; margin:16px 0;
                                    border-radius:0 6px 6px 0;">
                            <p style="margin:0; color:#e65100; font-size:13px;">
                                Build log is attached to this email.
                                Please review and fix the failing tests.
                            </p>
                        </div>

                        <div style="text-align:center; margin-top:24px;">
                            <a href="${env.BUILD_URL}"
                               style="background:#e53935; color:white;
                                      padding:12px 24px; text-decoration:none;
                                      border-radius:6px; font-weight:bold;
                                      font-size:14px; display:inline-block;
                                      margin-right:10px;">
                                View Build
                            </a>
                            <a href="${env.BUILD_URL}allure/"
                               style="background:#1976d2; color:white;
                                      padding:12px 24px; text-decoration:none;
                                      border-radius:6px; font-weight:bold;
                                      font-size:14px; display:inline-block;">
                                View Allure Report
                            </a>
                        </div>
                    </div>

                    <!-- Footer -->
                    <div style="background:#f9f9f9; padding:16px;
                                text-align:center; border-top:1px solid #eee;">
                        <p style="color:#888; font-size:12px; margin:0;">
                            Automated by Jenkins CI/CD Pipeline
                        </p>
                        <p style="color:#888; font-size:12px; margin:4px 0 0;">
                            Ripple Metering Automation Framework
                        </p>
                        <p style="color:#888; font-size:12px; margin:4px 0 0;">
                            Regards, Chakravarthi G
                        </p>
                    </div>

                </div>
                </body>
                </html>
                """
            )
        }

        cleanup {
            cleanWs()
        }
    }
}