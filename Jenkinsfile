//def agentImage = 'gradle:6.8.3-jdk11'

pipeline {

 agent any //{docker {image '${agentImage}'}}

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
      if (isUnix()) {
        sh 'gradle clean test'
        } else {
          bat 'gradle clean test'
        }
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
