properties([parameters([text(description: 'Specify telegram Chat IDs for notifications about this build: i.e. -612069995', name: 'chats')])])

def emailTo = params.targetEmail

pipeline {

 agent any

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

      echo "End of stage test!"
      }

      post {
        always {
          script {
            allure report: 'allure_reports', results: [[path: 'build/allure-results']]

            def chats = params.chats.readLines()
            chats.each { String chatID ->
            telegramSend(message: "Pipeline ${JOB_NAME} ${BUILD_NUMBER} - ${currentBuild.currentResult}, please check ${BUILD_URL}", chatId: chatID)
            }
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
