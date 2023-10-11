package com.epam.cucumber.steps;

import com.epam.api.models.Pet;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;

import static com.epam.cucumber.steps.RequestGetPetSteps.response;
import static com.epam.utils.FileHelpers.getJsonSchemaFromResource;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

@Slf4j
public class ResponseGetPetsSteps {
    public static long id;

    @Then("Response status code is {int}")
    public void validateResponseStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        log.info("Validating status code");
    }

    @And("Number of pets in response is greater then {int}")
    public void verifyPetsNumber(int minimumExpectedNumber) {
        Assertions.assertThat(response.then().extract().as(Pet[].class).length)
                .isGreaterThan(minimumExpectedNumber);
    }

    @And("Each pet object in response matches {string} schema")
    public void verifyPetArrayJsonSchema(String jsonSchemaName) {
        response.then().assertThat()
                .body(matchesJsonSchema(getJsonSchemaFromResource(jsonSchemaName)));
    }

    @And("Save {int} pet's id number in the list")
    public void savePetIdByNumber(int number) {
        id = response.then().extract().as(Pet[].class)[number-1].getId();
        log.info("Id of " + number + "pet is " + id);
    }

    @Then("Pet object in response matches {string} schema")
    public void verifyPetJsonSchema(String schema) {
        response.then().assertThat()
                .body(matchesJsonSchema(getJsonSchemaFromResource(schema)));
    }

}
