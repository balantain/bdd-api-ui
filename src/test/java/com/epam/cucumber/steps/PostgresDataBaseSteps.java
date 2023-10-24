package com.epam.cucumber.steps;

import com.epam.api.models.ModelBuilder;
import com.epam.api.models.Pet;
import com.epam.database.dao.PetDao;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.epam.cucumber.steps.RequestGetPetSteps.response;
@Slf4j
public class PostgresDataBaseSteps {
    private final PetDao petDao = new PetDao();

    public static List<Pet> pets = new ArrayList<>();

    @When("Insert saved Pet into database")
    public void insertPetiIntoDataBase() {
        petDao.insertPetIntoDatabase(ResponseGetPetsSteps.pet);
        log.info("Inserting pet %s into database".formatted(ResponseGetPetsSteps.id));
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
        pets = Arrays.stream(petsFromResponce).limit(amount).toList();
        pets.forEach(petDao::insertPetIntoDatabase);
        log.info("%s pets are saved into database".formatted(pets));
    }

    @When("Print pets with the following parameters:")
    public void createPetsWithParameters(List<Map<String, String>> pets) {
        ModelBuilder.buildPetWithData(pets).forEach(System.out::println);
    }
}
