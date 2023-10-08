package com.epam.api;

import com.epam.api.configuration.ConfigurationManager;
import com.epam.api.controllers.RequestController;
import com.epam.api.models.Pet;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.epam.api.enums.PetStatus.AVAILABLE;
import static com.epam.api.enums.PetStatus.SOLD;

public class GetTests {
    RequestSpecification requestSpecification;
    RequestController requestController;

    @BeforeMethod
    public void configureRequest() {
        requestSpecification = ConfigurationManager.getConfig();
        requestController = new RequestController(requestSpecification);
    }

    @Test
    public void getPetByStatusTest() {
        Pet[] pets = requestController.getPetByStatus(AVAILABLE, SOLD)
                .then().statusCode(200)
                .log().all().extract().as(Pet[].class);
        Assertions.assertThat(pets.length).isGreaterThan(0);
    }

    @Test
    public void getEmptyStatus() {
        RequestController requestController = new RequestController(requestSpecification);
        Pet[] pets = requestController.getPetByStatus()
                .then().statusCode(200)
                .log().all().extract().as(Pet[].class);
        Assertions.assertThat(pets).isEmpty();
    }

    @Test
    public void getPetByInvalidStatus() {
        RequestController requestController = new RequestController(requestSpecification);
        Pet[] pets = requestController.getPetByStatus("dead")
                .then().statusCode(200)
                .log().all().extract().as(Pet[].class);
        Assertions.assertThat(pets).isEmpty();
    }
}
