pipeline {
    agent any

    tools {
        maven 'Maven_3.8'
        jdk   'JDK_11'
    }

    // ── Daily trigger at 6:00 AM ──────────────────────────────
    triggers {
        cron('0 6 * * *')
    }

    parameters {
        choice(
            name: 'SUITE',
            choices: ['testng', 'smoke', 'regression', 'all-roles'],
            description: 'Select test suite to run'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                echo '✅ Code checked out from GitHub successfully'
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Building project...'
                bat 'mvn clean compile -q'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Running tests...'
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
            echo "📊 Build finished: ${currentBuild.currentResult}"
            archiveArtifacts artifacts: 'allure-results/**',
                             allowEmptyArchive: true
        }

        success {
            emailext (
                subject: "✅ PASSED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                to: "sprdmntester@gmail.com, devender.ripple@gmail.com, chakravarthi@ripplemetering.com, preethi@ripplemetering.com",
                body: """
                <html>
                <body style="font-family: Arial, sans-serif; background:#f4f4f4; padding:20px;">
                    <div style="max-width:600px; margin:auto; background:white;
                                border-radius:10px; overflow:hidden;
                                box-shadow:0 2px 10px rgba(0,0,0,0.1);">

                        <!-- Header -->
                        <div style="background:#00c853; padding:24px; text-align:center;">
                            <h1 style="color:white; margin:0; font-size:26px;">
                                ✅ Build Passed
                            </h1>
                            <p style="color:#e8f5e9; margin:8px 0 0;">
                                Ripple Metering Automation
                            </p>
                        </div>

                        <!-- Body -->
                        <div style="padding:24px;">
                            <p style="color:#333;">Hi Team,</p>
                            <p style="color:#333;">
                                Good news! All tests have <b style="color:#00c853;">PASSED</b>
                                successfully.
                            </p>

                            <table style="width:100%; border-collapse:collapse; margin:16px 0;">
                                <tr style="background:#f9f9f9;">
                                    <td style="padding:12px; border:1px solid #eee;
                                               font-weight:bold; width:40%; color:#555;">
                                        Job Name
                                    </td>
                                    <td style="padding:12px; border:1px solid #eee; color:#333;">
                                        ${env.JOB_NAME}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding:12px; border:1px solid #eee;
                                               font-weight:bold; color:#555;">
                                        Build Number
                                    </td>
                                    <td style="padding:12px; border:1px solid #eee; color:#333;">
                                        #${env.BUILD_NUMBER}
                                    </td>
                                </tr>
                                <tr style="background:#f9f9f9;">
                                    <td style="padding:12px; border:1px solid #eee;
                                               font-weight:bold; color:#555;">
                                        Status
                                    </td>
                                    <td style="padding:12px; border:1px solid #eee;">
                                        <span style="background:#00c853; color:white;
                                                     padding:4px 12px; border-radius:20px;
                                                     font-size:13px; font-weight:bold;">
                                            PASSED ✅
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding:12px; border:1px solid #eee;
                                               font-weight:bold; color:#555;">
                                        Duration
                                    </td>
                                    <td style="padding:12px; border:1px solid #eee; color:#333;">
                                        ${currentBuild.durationString}
                                    </td>
                                </tr>
                                <tr style="background:#f9f9f9;">
                                    <td style="padding:12px; border:1px solid #eee;
                                               font-weight:bold; color:#555;">
                                        Build URL
                                    </td>
                                    <td style="padding:12px; border:1px solid #eee;">
                                        <a href="${env.BUILD_URL}"
                                           style="color:#1976d2;">
                                            Click here to view
                                        </a>
                                    </td>
                                </tr>
                            </table>

                            <div style="text-align:center; margin-top:24px;">
                                <a href="${env.BUILD_URL}"
                                   style="background:#00c853; color:white; padding:12px 32px;
                                          text-decoration:none; border-radius:6px;
                                          font-weight:bold; font-size:15px;">
                                    View Build Details
                                </a>
                            </div>
                        </div>

                        <!-- Footer -->
                        <div style="background:#f9f9f9; padding:16px;
                                    text-align:center; border-top:1px solid #eee;">
                            <p style="color:#888; font-size:12px; margin:0;">
                                Automated by Jenkins CI/CD |
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
                subject: "❌ FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                to: "sprdmntester@gmail.com, devender.ripple@gmail.com, chakravarthi@ripplemetering.com, preethi@ripplemetering.com",
                attachLog: true,
                body: """
                <html>
                <body style="font-family: Arial, sans-serif; background:#f4f4f4; padding:20px;">
                    <div style="max-width:600px; margin:auto; background:white;
                                border-radius:10px; overflow:hidden;
                                box-shadow:0 2px 10px rgba(0,0,0,0.1);">

                        <!-- Header -->
                        <div style="background:#e53935; padding:24px; text-align:center;">
                            <h1 style="color:white; margin:0; font-size:26px;">
                                ❌ Build Failed
                            </h1>
                            <p style="color:#ffebee; margin:8px 0 0;">
                                Ripple Metering Automation
                            </p>
                        </div>

                        <!-- Body -->
                        <div style="padding:24px;">
                            <p style="color:#333;">Hi Team,</p>
                            <p style="color:#333;">
                                The automation build has
                                <b style="color:#e53935;">FAILED</b>.
                                Please check the logs attached.
                            </p>

                            <table style="width:100%; border-collapse:collapse; margin:16px 0;">
                                <tr style="background:#f9f9f9;">
                                    <td style="padding:12px; border:1px solid #eee;
                                               font-weight:bold; width:40%; color:#555;">
                                        Job Name
                                    </td>
                                    <td style="padding:12px; border:1px solid #eee; color:#333;">
                                        ${env.JOB_NAME}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding:12px; border:1px solid #eee;
                                               font-weight:bold; color:#555;">
                                        Build Number
                                    </td>
                                    <td style="padding:12px; border:1px solid #eee; color:#333;">
                                        #${env.BUILD_NUMBER}
                                    </td>
                                </tr>
                                <tr style="background:#f9f9f9;">
                                    <td style="padding:12px; border:1px solid #eee;
                                               font-weight:bold; color:#555;">
                                        Status
                                    </td>
                                    <td style="padding:12px; border:1px solid #eee;">
                                        <span style="background:#e53935; color:white;
                                                     padding:4px 12px; border-radius:20px;
                                                     font-size:13px; font-weight:bold;">
                                            FAILED ❌
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding:12px; border:1px solid #eee;
                                               font-weight:bold; color:#555;">
                                        Duration
                                    </td>
                                    <td style="padding:12px; border:1px solid #eee; color:#333;">
                                        ${currentBuild.durationString}
                                    </td>
                                </tr>
                                <tr style="background:#f9f9f9;">
                                    <td style="padding:12px; border:1px solid #eee;
                                               font-weight:bold; color:#555;">
                                        Build URL
                                    </td>
                                    <td style="padding:12px; border:1px solid #eee;">
                                        <a href="${env.BUILD_URL}"
                                           style="color:#1976d2;">
                                            Click here to view
                                        </a>
                                    </td>
                                </tr>
                            </table>

                            <div style="background:#fff3e0; border-left:4px solid #ff9800;
                                        padding:12px 16px; margin:16px 0;
                                        border-radius:0 6px 6px 0;">
                                <p style="margin:0; color:#e65100; font-size:13px;">
                                    ⚠️ Build log is attached to this email.
                                    Please review and fix the failing tests.
                                </p>
                            </div>

                            <div style="text-align:center; margin-top:24px;">
                                <a href="${env.BUILD_URL}"
                                   style="background:#e53935; color:white; padding:12px 32px;
                                          text-decoration:none; border-radius:6px;
                                          font-weight:bold; font-size:15px;">
                                    View Failure Details
                                </a>
                            </div>
                        </div>

                        <!-- Footer -->
                        <div style="background:#f9f9f9; padding:16px;
                                    text-align:center; border-top:1px solid #eee;">
                            <p style="color:#888; font-size:12px; margin:0;">
                                Automated by Jenkins CI/CD |
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