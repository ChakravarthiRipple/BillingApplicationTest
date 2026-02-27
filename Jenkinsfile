pipeline {
    agent any

    tools {
        maven 'Maven_3.8'
        jdk   'JDK_11'
    }

    // ── Daily schedule: runs every day at 6:00 AM ─────────────
    triggers {
        cron('0 6 * * *')        // 6:00 AM every day
        // cron('0 6 * * 1-5')   // weekdays only - uncomment if preferred
    }

    environment {
        GITHUB_REPO    = 'https://github.com/YOUR_USERNAME/selenium-framework.git'
        BRANCH         = 'main'
        ALLURE_RESULTS = 'allure-results'
        REPORT_DIR     = 'test-output'
    }

    parameters {
        choice(
            name: 'SUITE',
            choices: ['all-roles', 'smoke', 'regression'],
            description: 'Select which test suite to run'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run Chrome in headless mode'
        )
    }

    stages {

        stage('Checkout from GitHub') {
            steps {
                echo "Checking out branch: ${env.BRANCH}"
                git branch: "${env.BRANCH}",
                    url: "${env.GITHUB_REPO}"
                    // credentialsId: 'github-credentials'  // uncomment if private repo
            }
        }

        stage('Build') {
            steps {
                echo 'Compiling project...'
                sh 'mvn clean compile -q'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Running suite: ${params.SUITE} | Headless: ${params.HEADLESS}"
                sh """
                    mvn test \
                        -Dsuite=${params.SUITE} \
                        -Dheadless=${params.HEADLESS} \
                        -Dbrowser=chrome
                """
            }
            post {
                always {
                    // Publish TestNG results
                    junit allowEmptyResults: true,
                          testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh 'mvn allure:report -q'
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
            echo "Pipeline finished. Suite: ${params.SUITE}"
            // Archive Extent HTML report
            archiveArtifacts artifacts: 'test-output/*.html', allowEmptyArchive: true
        }
        failure {
            echo 'Build FAILED!'
            // Uncomment to enable email notifications:
            // emailext (
            //     subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER} [${params.SUITE}]",
            //     body: """
            //         Suite:   ${params.SUITE}
            //         Result:  FAILED
            //         Details: ${env.BUILD_URL}
            //     """,
            //     to: 'your-team@example.com'
            // )
        }
        success {
            echo 'All tests passed!'
        }
        cleanup {
            cleanWs()
        }
    }
}
