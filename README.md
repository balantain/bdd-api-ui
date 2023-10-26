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