package com.epam.cucumber.stepdefinitions.api;

import com.epam.api.configuration.RestConfig;
import com.epam.api.filters.FilterManager;
import com.epam.api.managers.RequestManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import static com.epam.cucumber.stepdefinitions.ModelSteps.petModel;

@Slf4j
public class BaseApiSteps {
    public static RequestManager requestManager;

    @Given("Use base request specification")
    public void getBaseApiConfiguration() {
        RequestSpecification requestSpecification = RestConfig.getConfig(FilterManager.getAllureLoggingFilter());
        requestManager = new RequestManager(requestSpecification);
        log.info("Base request specification is created with AllureLoggingFilter");
    }

    @When("Created pet is added to request body")
    public void addSinglePetToRequestBody() {
        requestManager.addPetToRequestBody(petModel);
    }
}
