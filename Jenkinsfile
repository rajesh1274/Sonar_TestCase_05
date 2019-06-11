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
        sshagent(['tomcat']) {
          ls
          sh 'scp -o StrictHostKeyChecking=no target/*.war admin@172.30.112.5:/Program Files/Apache Software Foundation/Tomcat 8.5/webapps/webapp.war'
        }      
      }       
    }
    
  }
}
