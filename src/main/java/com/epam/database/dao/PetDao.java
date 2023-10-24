package com.epam.database.dao;

import com.epam.api.models.Pet;
import com.epam.database.configuration.PostgresConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class PetDao {
    PostgresConnectionFactory factory = new PostgresConnectionFactory();

    private static final String INSERT_PET_QUERY = "INSERT INTO Pets (pet_id, category_id, category_name, name, photourls, tags_id, tags_name, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public void insertPetIntoDatabase(Pet pet) {
        try (Connection connection = factory.getConnection()){
            PreparedStatement statement = connection.prepareStatement(INSERT_PET_QUERY);
            log.info("PreparedStatement: " + INSERT_PET_QUERY);
            statement.setLong( 1, pet.getId());
            log.info("1 parameter: " + pet.getId());
            statement.setLong(2, pet.getCategory().getId());
            log.info("2 parameter:" + pet.getCategory().getId());
            statement.setString(3, pet.getCategory().getName());
            log.info("3 parameter:" + pet.getCategory().getName());
            statement.setString(4, pet.getName());
            log.info("4 parameter:" + pet.getName());
            statement.setArray(5, connection.createArrayOf("text", pet.getPhotoUrls()));
            log.info("5 parameter: " + Arrays.toString(pet.getPhotoUrls()));
            List<Long> tagIdsList = new ArrayList<>();
            List<String> tagNamesList = new ArrayList<>();
            Arrays.stream(pet.getTags()).forEach(tag -> {
                tagIdsList.add(tag.getId());
                tagNamesList.add(tag.getName());
                log.info("Tag parameters are: key: " + tag.getId() + ", value: " + tag.getName());
            });
            statement.setArray(6, connection.createArrayOf("bigint", tagIdsList.toArray()));
            statement.setArray(7, connection.createArrayOf("text", tagNamesList.toArray()));
            statement.setString(8, pet.getStatus());
            log.info("8 parameter: " + pet.getStatus());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
