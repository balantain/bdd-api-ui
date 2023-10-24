package com.epam.cucumber.steps;

import com.epam.api.configuration.RestConfig;
import com.epam.api.managers.RequestManager;
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
    private RequestManager requestController;
    private String[] statuses;
    public static Response response;

    @Given("Use base request specification")
    public void getBaseApiConfiguration() {
        RequestSpecification requestSpecification = RestConfig.getConfig(FilterManager.getAllureLoggingFilter());
        requestController = new RequestManager(requestSpecification);
        log.info("Base request specification is created with AllureLoggingFilter");
    }

    @And("Pet's desired statuses are:")
    public void setPetStatuses(List<String> statuses) {
        this.statuses = statuses.toArray(new String[0]);
        log.info("Statuses %s are added to params".formatted(statuses));
    }

    @When("Trigger GET request with {string} endpoint")
    public void getPetsByPreparedStatus(String endpoint) {
        response = requestController.getPetByStatusWithEndpoint(endpoint, statuses);
        log.info("Endpoint %s is triggered with statuses: %s".formatted(endpoint, Arrays.toString(statuses)));
    }

    @When("Trigger GET request with {string} endpoint with saved id")
    public void getPetByIdWithEndpoint(String endpoint) {
        response = requestController.getPetByIdWithEndpoint(endpoint, ResponseGetPetsSteps.id);
        log.info("Endpoint %s is triggered with id %s".formatted(endpoint, ResponseGetPetsSteps.id));
    }
}
