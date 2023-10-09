pipeline {
    agent any

    stages {
        stage('API tests') {
            steps {
                sh "mvn -DsuiteXmlFile=cucumber.xml -Dcucumber.filter.tags=@GetPetsTests test"
            }
        }
    }
}