package com.epam.cucumber.steps;

import com.epam.api.configuration.ConfigurationManager;
import com.epam.api.controllers.RequestController;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class RequestGetPetSteps {
    private RequestController requestController;
    private String[] statuses;
    public static Response response;

    @Given("Base request specification")
    public void getBaseApiConfiguration() {
        RequestSpecification requestSpecification = ConfigurationManager.getConfig();
        requestController = new RequestController(requestSpecification);
    }

    @And("Pet statuses are:")
    public void setPetStatuses(List<String> statuses) {
        this.statuses = statuses.toArray(new String[0]);
    }

    @When("User triggers GET request with {string} endpoint")
    public void getPetsByPreparedStatus(String endpoint) {
        response = requestController.getPetByStatusWithEndpoint(endpoint, statuses);
    }
}
