package com.epam.cucumber.stepdefinitions.api;

import com.epam.api.configuration.RestConfig;
import com.epam.api.managers.RequestManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import static com.epam.cucumber.stepdefinitions.ModelSteps.petModel;

@Slf4j
public class BaseApiSteps {
    public static RequestSpecification requestSpecification;
    public static RequestManager requestManager;

    @Given("Use base request specification")
    public void getBaseApiConfiguration() {
        RequestSpecification requestSpecification = RestConfig.getConfig();
        requestManager = new RequestManager(requestSpecification);
        log.info("Base request specification is created with AllureLoggingFilter");
    }

    @Given("Use customised request specification")
    public void getCustomizedConfiguration() {
        requestSpecification = RestConfig.getConfig();
        log.info("Base request specification is created with AllureLoggingFilter");
    }

    @And("Request specification is ready for request")
    public void getRequestManager() {
        requestManager = new RequestManager(requestSpecification);
    }

    @And("Add headers to request:")
    public void addHeaders(List<Map<String, String>> headers) {
        headers.forEach(header -> requestSpecification.header(header.get("header"), header.get("value")));
        log.info("Headers were added to request specification");
    }

    @And("Created pet is added to request body")
    public void addSinglePetToRequestBody() {
        requestSpecification.body(petModel);
        log.info("Created Pet is added to body: " + petModel);
    }
}
