package com.epam.cucumber.stepdefinitions;

import com.epam.api.models.ModelBuilder;
import com.epam.api.models.Pet;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModelSteps {
    public static Pet petModel;
    public static List<Pet> petModelsList = new ArrayList<>();

    @When("Pet objects with the folowing data are created:")
    public void createPetsWithData(List<Map<String, String>> petData) {
        petModelsList = ModelBuilder.buildPetWithData(petData);
    }

    @When("Single Pet object with the folowing data is created:")
    public void createSinglePetWithData(List<Map<String, String>> petData) {
        petModel = ModelBuilder.buildPetWithData(petData).get(0);
    }

    /**
     * Temporarily step to check pets creation
     */
    @When("Print pets with the following parameters:")
    public void createPetsWithParameters(List<Map<String, String>> pets) {
        ModelBuilder.buildPetWithData(pets).forEach(System.out::println);
    }
}
