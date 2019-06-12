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
    
    stage ('Deploy-To-Tomcat') {
      steps {
        sh 'ls -lrt'
        sh 'cp target/*.jar /home/rajesh4debug/prod/apache-tomcat-8.5.42/webapps/webapp.jar'
          
      }       
    }
    
  }
}
