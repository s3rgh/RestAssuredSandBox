//def agentImage = 'gradle:6.8.3-jdk11'
def emailTo = params.toEmails

 pipeline {

   options {
     buildDiscarder(logRotator(numToKeepStr: '20'))
     timestamps()
     disableConcurrentBuilds()
   }

   agent any

   stages {
     stage('Run tests') {
       steps {
       echo "Start tests!"
       echo "Testing..."
       bat 'gradle clean doTest'
       echo "End of stage test!"
       }
     }
       post {
         always {
           script {
             allure report: 'allure_reports', results: [[path: 'build/allure-results']]
           }
           script {
                       cest = TimeZone.getTimeZone("CEST")
                       def cest = new Date()
                       println(cest)
                       def jobName = currentBuild.fullDisplayName
                       env.Name = Name
                       env.cest = cest
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

