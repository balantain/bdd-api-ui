package com.epam.cucumber.steps;

import com.epam.api.models.Pet;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
@Slf4j
public class ResponseGetPetsSteps {

    private final Response response = RequestGetPetSteps.response;

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
}
