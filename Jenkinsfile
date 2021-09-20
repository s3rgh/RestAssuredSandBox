def agentImage = 'gradle:6.8.3-jdk11'

pipeline {

  options {
    buildDiscarder(logRotator(numToKeepStr: '20'))
    timestamps()
    disableConcurrentBuilds()
  }

  stages {
    stage('Run tests') {
      steps {
        script {
          docker.image("${agentImage}").inside() {
            sh 'gradle clean test --no-daemon'
          }
        }
      }
      post {
        always {
          script {
            allure report: 'allure_reports', results: [[path: 'build/allure-results']]
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
