//def agentImage = 'gradle:6.8.3-jdk11'
properties([parameters([text(description: 'Specify telegram Chat IDs for notifications about this build: i.e. -612069995', name: 'chats')])])

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
      bat 'gradle clean doTest --no-daemon'

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
                      // Send Notification for list of telegram chats provided as multi-line string parameter on Jenkins
                                  def chats = params.chats.readLines()
                                  chats.each { String chatID ->
                                  telegramSend(message: "Pipeline ${env.JOB_NAME} ${env.BUILD_NUMBER} - ${currentBuild.currentResult}, please check ${env.BUILD_URL}", chatId: chatID)
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
