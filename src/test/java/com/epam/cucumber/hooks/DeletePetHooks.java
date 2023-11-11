package com.epam.cucumber.hooks;

import com.epam.api.configuration.RestConfig;
import com.epam.api.filters.FilterManager;
import com.epam.api.managers.RequestManager;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import static com.epam.cucumber.stepdefinitions.ModelSteps.petModel;

@Slf4j
public class DeletePetHooks {
    @After("@PostNewCustomPet")
    public void deleteCreatedPet(Scenario scenario) {
        log.info("Deleting pet from \"%s\" scenario".formatted(scenario.getName()));
        RequestSpecification requestSpecification = RestConfig.getConfig(FilterManager.getAllureLoggingFilter());
        RequestManager requestManager = new RequestManager(requestSpecification);
        requestManager.deletePet(petModel.getId());
    }
}
