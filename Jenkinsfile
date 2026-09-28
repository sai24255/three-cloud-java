pipeline {
    agent any

    environment {
        IMAGE_NAME = 'three-cloud-java'
        IMAGE_TAG  = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Maven Package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    docker build \
                      -t ${IMAGE_NAME}:${IMAGE_TAG} \
                      -t ${IMAGE_NAME}:latest \
                      .
                '''
            }
        }

        stage('Docker Test') {
            steps {
                sh '''
                    docker rm -f ${IMAGE_NAME}-test 2>/dev/null || true

                    docker run -d \
                      --name ${IMAGE_NAME}-test \
                      -p 18080:8080 \
                      ${IMAGE_NAME}:${IMAGE_TAG}

                    sleep 10

                    curl --fail http://localhost:18080/actuator/health

                    docker rm -f ${IMAGE_NAME}-test
                '''
            }
        }
    }

    post {
        always {
            sh 'docker rm -f ${IMAGE_NAME}-test 2>/dev/null || true'
        }

        success {
            echo 'BUILD SUCCESSFUL'
        }

        failure {
            echo 'BUILD FAILED'
        }
    }
}
