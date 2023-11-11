# bdd-api-ui

### Run BDD tests with cucumber comand:
mvn -DsuiteXmlFile=cucumber.xml -Dcucumber.filter.tags=@GetPetsTests clean test

### Generate and see Allure report:
mvn allure:report
mvn allure:serve

### PostgreSQL database info:
database name: petshop_postgres_db
tables: 
1. pets
   
create query:

    CREATE TABLE Pets (                                                          
        id serial PRIMARY KEY,
        pet_id bigint,
        category_id bigint,
        category_name text,
        name text,
        photoUrls text[],
        tags_id bigint[],
        tags_name text[],
        status text
    );

# Cucumber classes:

## stepdefinitions:
    
Currently there are 4 main groups of stepdefinition:

* ModelSteps

These are steps related to any action with business models, like Pet, Store, User. 
All models, which are created using this steps are saved in a static variable withing its class.

* DatabaseSteps

These are steps related to any action with database, like adding, deleting, updating data in database. 
If you want to save pet into database, it is possible to do this with several ways. Using special step
from database steps with custom data. Or you can create a pet using model steps and then add it into database.

* BaseApiSteps

These are steps for creating and modifying request, like creating request specification, adding different parameters to request,
adding body etс. Just pay attention at the difference in creating and customizing request:

    Use base request specification

This step uses base request specification and initialize RequestManager at the same time. 

Step:

    Use customised request specification

This step uses base request specification, but doesn't initialize RequestManager, giving an opportunity to customaze request specification
according to your needs. (adding headers, parameters etc). When all customizations are done, it is neccessary to use following step to initialize RequestManager;

    Request specification is ready for request

This was made to get rid of the need to initialize RequestManager before every request.

NOTE:

There is also an opportunity to add filters with different parameters while creating base request specification, but from my perspective it is not comfortable in BDD

* PetApiSteps

These are steps for all actions related to Pet endpoints. Sending request and validating response.

# API classes

Currently API classes can be devided into 3 main layers

* Configuration layer

Configuration layer is represented by RestConfig class, which has getConfig() method. This Method provides base RequestSpecification,
and can accept different filters as a parameters. For allure reports it is neccessary to pass AllureLoggingFilter (FilterManager.getAllureLoggingFilter())

* Request layer

Request layer is represented by RequestManager class. It contains main business GET, POST, PUT, DELETE request methods. All of them return Response.

All validation actions are located in Test classes, but it would be nice to have separate layer for this purpose.