package com.epam.cucumber.stepdefinitions.api.pet;

import com.epam.api.models.Pet;
import com.epam.api.models.ResponseError;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.epam.cucumber.stepdefinitions.ModelSteps.petModel;
import static com.epam.cucumber.stepdefinitions.api.BaseApiSteps.requestManager;
import static com.epam.utils.FileHelpers.getJsonSchemaFromResource;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class PetApiSteps {
    public static Response response;
    private String[] statuses = new String[0];
    public static long idFromResponse;
    private Pet petFromResponse;

    @When("Pet's desired statuses are:")
    public void setPetStatuses(List<String> statuses) {
        log.info("Adding statuses %s to params".formatted(statuses));
        this.statuses = statuses.toArray(new String[0]);
    }

    @When("Trigger GET request with {string} endpoint")
    public void getPetsByPreparedStatus(String endpoint) {
        log.info("Triggering %s endpoint with statuses: %s".formatted(endpoint, Arrays.toString(statuses)));
        response = requestManager.getPetByStatusWithEndpoint(endpoint, statuses);
    }

    @When("Trigger GET request with {string} endpoint with saved id")
    public void getPetByIdWithEndpoint(String endpoint) {
        log.info("Triggering %s endpoint with id %s".formatted(endpoint, idFromResponse));
        response = requestManager.getPetByIdWithEndpoint(endpoint, idFromResponse);
    }

    @When("Trigger GET request with {string} endpoint with id {long}")
    public void getPetByIdWithEndpoint(String endpoint, long id) {
        log.info("Triggeting %s endpoint with id %s".formatted(endpoint, id));
        response = requestManager.getPetByIdWithEndpoint(endpoint, id);
    }

    @When("Trigger GET request with {string} endpoint with created pet id")
    public void getPetByCreatedPetId(String endpoint) {
        log.info("Triggering %s endpoint with id %s".formatted(endpoint, petModel.getId()));
        response = requestManager.getPetByIdWithEndpoint(endpoint, petModel.getId());
    }

    @When("Save pet from response into constant")
    public void savePateFromResponseIntoConstant() {
        log.info("Saving %s from response into constant".formatted(petFromResponse));
        petFromResponse = response.then().extract().as(Pet.class);
    }

    @When("Save {int} pet's id number in the list")
    public void savePetIdByNumber(int number) {
        log.info("Id of %s pet is %s".formatted(number, idFromResponse));
        idFromResponse = response.then().extract().as(Pet[].class)[number-1].getId();
    }

    @When("Save {int} Pet from response into constant")
    public void savePateintoConstant(int number) {
        log.info("Saving %s pet from response into constant: %s".formatted(number, petFromResponse));
        petFromResponse = response.then().extract().as(Pet[].class)[number-1];
    }

    @When("Trigger POST request with {string} endpoint with body")
    public void postPetWithBody(String endpoint) {
        log.info("Triggering POST request with %s pet".formatted(petModel));
        response = requestManager.postNewPet(endpoint, petModel);
    }

    @When("Trigger POST request with {string} endpoint")
    public void postPet(String endpoint) {
        log.info("Triggering POST request for %s endpoint".formatted(endpoint));
        response = requestManager.postNewPet(endpoint);
    }

    @Then("Response status code is {int}")
    public void validateResponseStatusCode(int statusCode) {
        log.info("Validating status code");
        response.then().statusCode(statusCode);
    }

    @Then("Response body matches {string} schema")
    public void verifyPetJsonSchema(String schema) {
        log.info("Validate response matches json schema");
        response.then().assertThat()
                .body(matchesJsonSchema(getJsonSchemaFromResource(schema)));
    }

    @Then("Saved pet from response is equal to created pet")
    public void validateResponseBodyWithCreatedPet() {
        log.info("Validate response body is equal to created pet");
        Assertions.assertThat(petFromResponse).isEqualTo(petModel);
    }

    @Then("Number of pets in response is greater then {int}")
    public void verifyPetsNumber(int minimumExpectedNumber) {
        Assertions.assertThat(response.then().extract().as(Pet[].class).length)
                .as("Number of pets in response should be greater than %s".formatted(minimumExpectedNumber))
                .isGreaterThan(minimumExpectedNumber);
    }

    @Then("Responce body has empty array")
    public void verifyResponseEmptyArray() {
        response.then().body(equalTo("[]"));
//        response.then().body(emptyArray()); TODO WHY THIS ASSERTION FAILS?
    }

    @Then("Response headers amount is {int}")
    public void validateResponseHeadersAmount(int amount) {
        Assertions.assertThat(response.headers().size())
                .as("Amount of headers should be equal to %s".formatted(amount)).isEqualTo(amount);
    }

    @Then("Response headers are equal to:")
    public void validateResponseHeaders(List<Map<String, String>> headers) {
        SoftAssertions soft = new SoftAssertions();
        headers.forEach(headerMap-> soft.assertThat(response.header(headerMap.get("header"))).isEqualTo(headerMap.get("value")));
        soft.assertAll();
    }

    @Then("Response headers matches:")
    public void validateResponseHeadersmatches(List<Map<String, String>> headers) {
        SoftAssertions soft = new SoftAssertions();
        headers.forEach(headerMap-> soft.assertThat(response.header(headerMap.get("header"))).matches(headerMap.get("regex")));
        soft.assertAll();
    }

    @Then("Error has code {int}, type {string} and message {string}")
    public void validateErrorObject(int code, String type, String message) {
        SoftAssertions soft = new SoftAssertions();
        ResponseError error = response.then().extract().body().as(ResponseError.class);
        soft.assertThat(error.getCode()).isEqualTo(code);
        soft.assertThat(error.getType()).isEqualTo(type);
        soft.assertThat(error.getMessage()).isEqualTo(message);
        soft.assertAll();
    }
}
