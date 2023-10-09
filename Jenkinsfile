pipeline {
    agent any

    stages {
        stage('API tests') {
            steps {
                sh "mvn -DsuiteXmlFile=cucumber.xml -Dcucumber.filter.tags=@GetPetsTests test"
            }
            post {
                always {
                    allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
                }
            }
        }
    }
}