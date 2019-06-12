pipeline {
  agent any 
  tools {
    maven 'Maven'
  }
  stages {
    stage ('Initialize') {
      steps {
        sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
            ''' 
      }
    }   
    stage ('Build') {
      steps {
        sh 'mvn clean package'
       }
    }
    
    stage ('Check-Git-Secrets') {
      steps {
        sh 'rm trufflehog || true && whoami'
        sh 'docker run gesellix/trufflehog --json https://github.com/rajesh1274/Sonar_TestCase_05.git > trufflehog'
        sh 'cat trufflehog'
      }
    }
    
    stage ('Deploy-To-Tomcat') {
      steps {
        sh 'pwd'
        sh 'sudo cp target/*.jar /home/rajesh4debug/prod/apache-tomcat-8.5.42/webapps/Sonar_TestCase_05.jar'
          
      }       
    }
    
  }
}
