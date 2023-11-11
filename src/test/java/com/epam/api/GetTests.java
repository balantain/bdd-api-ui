package com.epam.api;

import com.epam.api.configuration.RestConfig;
import com.epam.api.managers.RequestManager;
import com.epam.api.models.Pet;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.epam.api.enums.PetStatus.AVAILABLE;
import static com.epam.api.enums.PetStatus.SOLD;
import static com.epam.api.filters.FilterManager.getAllureLoggingFilter;

public class GetTests {
    RequestSpecification requestSpecification;
    RequestManager requestManager;

    @BeforeMethod
    public void configureRequest() {
        requestSpecification = RestConfig.getConfig(getAllureLoggingFilter());
        requestManager = new RequestManager(requestSpecification);
    }

    @Test
    public void getPetByStatusTest() {
        Pet[] pets = requestManager.getPetByStatus(AVAILABLE, SOLD)
                .then().statusCode(200)
                .log().all().extract().as(Pet[].class);
        Assertions.assertThat(pets.length).isGreaterThan(0);
    }

    @Test
    public void getEmptyStatus() {
        RequestManager requestManager = new RequestManager(requestSpecification);
        Pet[] pets = requestManager.getPetByStatus()
                .then().statusCode(200)
                .log().all().extract().as(Pet[].class);
        Assertions.assertThat(pets).isEmpty();
    }

    @Test
    public void getPetByInvalidStatus() {
        RequestManager requestManager = new RequestManager(requestSpecification);
        Pet[] pets = requestManager.getPetByStatus("dead")
                .then().statusCode(200)
                .log().all().extract().as(Pet[].class);
        Assertions.assertThat(pets).isEmpty();
    }
}
