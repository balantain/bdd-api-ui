package com.epam.cucumber.stepdefinitions.database;

import com.epam.api.models.ModelBuilder;
import com.epam.api.models.Pet;
import com.epam.database.dao.PetDao;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.epam.cucumber.stepdefinitions.ModelSteps.petModel;
import static com.epam.cucumber.stepdefinitions.ModelSteps.petModelsList;
import static com.epam.cucumber.stepdefinitions.api.pet.PetApiSteps.idFromResponse;
import static com.epam.cucumber.stepdefinitions.api.pet.PetApiSteps.response;

@Slf4j
public class PostgresDataBaseSteps {
    private final PetDao petDao = new PetDao();

    @When("Insert saved Pet into database")
    public void insertPetiIntoDataBase() {
        petDao.insertPetIntoDatabase(petModel);
        log.info("Inserting pet %s into database".formatted(idFromResponse));
    }

    @When("Insert pets into database with values:")
    public void addCustomPetIntoDatabase(List<Map<String, String>> pets) {
        List<Pet> petsList = ModelBuilder.buildPetWithData(pets);
        petsList.forEach(petDao::insertPetIntoDatabase);
        log.info("%s pets are added into database".formatted(petsList));
    }

    @When("Save {int} first pets from response into database")
    public void savePetsArrayInDatabase(int amount) {
        Pet[] petsFromResponce = response.then().extract().as(Pet[].class);
        petModelsList = Arrays.stream(petsFromResponce).limit(amount).toList();
        petModelsList.forEach(petDao::insertPetIntoDatabase);
        log.info("%s pets are saved into database".formatted(petModelsList));
    }

    @When("Add pets with the following parameters into Database:")
    public void insertPetsIntoDatabase(List<Map<String, String>> pets) {
        pets.forEach(petDao::insertPetIntoDatabase);
    }
}
