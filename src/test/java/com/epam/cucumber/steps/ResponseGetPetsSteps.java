package com.epam.cucumber.steps;

import com.epam.api.models.Pet;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

import java.util.List;
import java.util.Map;

import static com.epam.cucumber.steps.RequestGetPetSteps.response;
import static com.epam.utils.FileHelpers.getJsonSchemaFromResource;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.*;

@Slf4j
public class ResponseGetPetsSteps {
    public static long id;
    public static Pet pet;


    @When("Save pet into constant")
    public void savePateFromResponseIntoConstant() {
        pet = response.then().extract().as(Pet.class);
        log.info("Save %s from response into constant".formatted(pet));
    }

    @When("Save {int} pet's id number in the list")
    public void savePetIdByNumber(int number) {
        id = response.then().extract().as(Pet[].class)[number-1].getId();
        log.info("Id of %s pet is %s".formatted(number, id));
    }

    @When("Save {int} Pet from response into constant")
    public void savePateintoConstant(int number) {
        pet = response.then().extract().as(Pet[].class)[number-1];
        log.info("Save %s pet from response into constant: %s".formatted(number, pet));
    }

    @Then("Response status code is {int}")
    public void validateResponseStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        log.info("Validating status code");
    }

    @Then("Each pet object in response matches {string} schema")
    public void verifyPetArrayJsonSchema(String jsonSchemaName) {
        response.then().assertThat()
                .body(matchesJsonSchema(getJsonSchemaFromResource(jsonSchemaName)));
    }

    @Then("Pet object in response matches {string} schema")
    public void verifyPetJsonSchema(String schema) {
        response.then().assertThat()
                .body(matchesJsonSchema(getJsonSchemaFromResource(schema)));
    }

    @Then("Number of pets in response is greater then {int}")
    public void verifyPetsNumber(int minimumExpectedNumber) {
        Assertions.assertThat(response.then().extract().as(Pet[].class).length)
                .isGreaterThan(minimumExpectedNumber);
    }

    @Then("Responce body has empty array")
    public void verifyResponseEmptyArray() {
        response.then().body(equalTo("[]"));
//        response.then().body(emptyArray()); TODO WHY THIS ASSERTION FAILS?
    }

    @Then("Response headers amount is {int}")
    public void validateResponseHeadersAmount(int amount) {
        Assertions.assertThat(response.headers().size()).isEqualTo(amount);
    }

    @Then("Response headers are equal to:")
    public void validateResponseHeaders(List<Map<String, String>> headers) {
        SoftAssertions soft = new SoftAssertions();
        headers.forEach(headerMap-> soft.assertThat(response.header(headerMap.get("header"))).isEqualTo(headerMap.get("value")));
        soft.assertAll();
    }

    @Then("Response headers matches:")
    public void  validateResponseHeadersmatches(List<Map<String, String>> headers) {
        SoftAssertions soft = new SoftAssertions();
        headers.forEach(headerMap-> soft.assertThat(response.header(headerMap.get("header"))).matches(headerMap.get("regex")));
        soft.assertAll();
    }
}
