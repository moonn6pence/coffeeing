/* groovylint-disable LineLength, NestedBlockDepth */
pipeline {
  agent any
  tools {
    gradle('gradle8.3')
  }

  stages {
    stage('Git Pull') {
      steps {
        git branch: 'dev/be', credentialsId: 'accessToken', url: 'https://lab.ssafy.com/s09-bigdata-recom-sub2/S09P22A204.git'
      }
    }
    stage('Pre Build Clean up') {
      steps {
        script {
          if (fileExists('backend/coffeeing/build')) {
            echo 'Build directory exists. REMOVING'
            fileOperations([folderDeleteOperation('backend/coffeeing/build')])
          }
        }
      }
    }
    stage('Copy Property Files') {
      steps {
        sh 'cp /home/ubuntu/dockerfiles/server/application-dev.yml /var/lib/jenkins/workspace/coffeeing_be_dev/backend/coffeeing/src/main/resources'
        sh 'cp /home/ubuntu/dockerfiles/server/application-test.yml /var/lib/jenkins/workspace/coffeeing_be_dev/backend/coffeeing/src/main/resources'
      }
    }
    stage('Gradlew Test') {
      steps {
        script {
          sh '''
            cd "${WORKSPACE}"/backend/coffeeing
            ./gradlew test
          '''
        }
      }
    }
    stage('SonarQube') {
      steps {
        withSonarQubeEnv(credentialsId: 'sonar_token', installationName: 'CoffeeingSonar') {
          sh '''
            cd "${WORKSPACE}"/backend/coffeeing
            ./gradlew sonar
          '''
        }
      }
    }
    stage('build jar') {
      steps {
        sh '''
          cd "${WORKSPACE}"/backend/coffeeing
          ./gradlew bootjar
        '''
      }
    }
    stage('Dockerize') {
      steps {
        script {
          sh '''
            sudo docker rm -f coffeeingServerBlue || true
            cd './backend/coffeeing'
            sudo docker build -t coffeeing-server-blue .
            sudo docker image prune -f
            sudo docker run --name coffeeingServerBlue -d --network host -e SPRING_PROFILES_ACTIVE=dev coffeeing-server-blue
          '''
        }
      }
    }
  }
}
