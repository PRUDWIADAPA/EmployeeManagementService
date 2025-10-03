pipeline {
    agent{
        docker{
            image 'maven:3.9.8-eclipse-temurin-21',
            reuseNode true
        }
    }

    stages {
        stage('Building') {
            steps {
                echo 'Building By Prudwi Ceo'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Testing')
        {
            parallel{
                stage('Unit Testing')
                {
                    steps{
                        echo 'Performing Unit Testing By Prudwi Ceo'
                        sh 'mvn test -DskipUnitTests'
                    }

                    post{
                        always{
                            junit '**/target/surefire-reports/*.xml'
                        }
                    }
                }
            }
        }
    }
}
