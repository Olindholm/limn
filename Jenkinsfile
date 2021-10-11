pipeline {
  agent {
    docker {
      image 'maven:3.8.1-adoptopenjdk-11'
      args '-v /root/.m2:/root/.m2'
    }

  }
  stages {
    stage('Build') {
      steps {
        echo 'Building...'
        sh 'bash ./build'
      }
    }

    stage('Test') {
      steps {
        echo 'Testing...'
        sh 'bash ./test --skip-clean'
      }
    }

    stage('Deploy') {
      when { branch 'master' }
      steps {
        echo 'Deploying...'
      }
    }

  }
  post {
    always {
      junit 'target/surefire-reports/*.xml'
    }
  }
}
