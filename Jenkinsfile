//def agentImage = 'gradle:6.8.3-jdk11'
def emailTo = params.targetEmail

pipeline {

 agent any // {docker {image "${agentImage}"}}

  options {
    buildDiscarder(logRotator(numToKeepStr: '20'))
    timestamps()
    disableConcurrentBuilds()
  }

  stages {
    stage('Run tests') {
      steps {
      echo "Start tests!"
      echo "Testing..."
      bat 'gradle clean doTest'

//        script {
//           docker.image("${agentImage}").inside() {
//             sh 'gradle clean test --no-daemon'
//           }
//        }

      echo "End of stage test!"
      }

      post {
        always {
          script {
            allure report: 'allure_reports', results: [[path: 'build/allure-results']]
            emailext body: '''${SCRIPT, template="allure-report.groovy"}''',
                    mimeType: 'text/html',
                    subject: "[Jenkins] Test Execution Summary",
                    to: "${emailTo}",
                    replyTo: "${emailTo}"
          }
          script {
                      allure report: 'allure_reports', results: [[path: 'build/allure-results']]
                      emailext body: '''${SCRIPT, template="allure-report.groovy"}''',
                              mimeType: 'text/html',
                              subject: "[Jenkins] Test Execution Summary",
                              to: "${emailTo}",
                              replyTo: "${emailTo}"
                    }
        }
      }
    }
  }
  post {
    cleanup {
      script {
        cleanWs()
      }
    }
  }
}
