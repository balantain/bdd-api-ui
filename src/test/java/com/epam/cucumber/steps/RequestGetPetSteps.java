package com.epam.cucumber.steps;

import com.epam.api.configuration.ConfigurationManager;
import com.epam.api.controllers.RequestController;
import com.epam.api.filters.FilterManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
@Slf4j
public class RequestGetPetSteps {
    private RequestController requestController;
    private String[] statuses;
    public static Response response;

    @Given("Base request specification")
    public void getBaseApiConfiguration() {
        RequestSpecification requestSpecification = ConfigurationManager.getConfig(FilterManager.getAllureLoggingFilter());
        requestController = new RequestController(requestSpecification);
        log.info("Base request specification is created with AllureLoggingFilter");
    }

    @And("Pet statuses are:")
    public void setPetStatuses(List<String> statuses) {
        this.statuses = statuses.toArray(new String[0]);
        log.info("Statuses " + statuses + " are added to params");
    }

    @When("User triggers GET request with {string} endpoint")
    public void getPetsByPreparedStatus(String endpoint) {
        response = requestController.getPetByStatusWithEndpoint(endpoint, statuses);
        log.info("Endpoint " + endpoint + " is triggered with statuses: " + Arrays.toString(statuses));
    }
}
