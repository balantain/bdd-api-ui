package com.epam.database.dao;

import com.epam.api.models.Category;
import com.epam.api.models.Pet;
import com.epam.api.models.Tag;
import com.epam.database.configuration.PostgresConnectionFactory;
import com.epam.utils.ResultSetHelper;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.epam.utils.SQLDataBaseUtils.*;
import static java.util.Objects.nonNull;

@Slf4j
public class PetDao {
    PostgresConnectionFactory factory = new PostgresConnectionFactory();

    private static final String INSERT_PET_QUERY =
            "INSERT INTO Pets (pet_id, category_id, category_name, name, photourls, tags_id, tags_name, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_PET_BY_NAME_QUERY =
            "SELECT * FROM Pets WHERE name = ?";

    private static final String DELETE_PET_BY_NAME_QUERY =
            "DELETE FROM Pets WHERE name = ?";

    private static final String GET_PET_BY_PET_ID_QUERY =
            "SELECT * FROM Pets WHERE pet_id = ?";

    private static final String DELETE_PET_BY_PET_ID_QUERY =
            "DELETE FROM Pets WHERE pet_id = ?";

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

    /**
     * This method reads parameters from Cucumber feature file and Insert them into Database.
     * obvious parameters are:
     * pet_id, defaultValue = 0;
     * name, default value = "string";
     * photourls, defaultValue = new String[0];
     */
    public void insertPetIntoDatabase(Map<String, String> petDataMap) {
        try(Connection connection = factory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_PET_QUERY);
            setLongIntoStatement(statement, 1, petDataMap.get("pet_id"), 0L);
            setLongIntoStatement(statement, 2, petDataMap.get("category_id"));
            statement.setString(3, petDataMap.get("category_name"));
            setStringIntoStatement(statement, 4, petDataMap.get("name"), "string");
            setTextArrayIntoStatement(connection, statement, 5, petDataMap.get("photourls"), new String[0]);
            if (nonNull(petDataMap.get("tags_id")) && nonNull(petDataMap.get("tags_name"))) {
                var intArray = Arrays.stream(petDataMap.get("tags_id").split(",")).map(Integer::parseInt)
                        .toArray();
                var stringArray = petDataMap.get("tags_name").split(",");
                if(intArray.length == stringArray.length) {
                    statement.setArray(6, connection.createArrayOf("bigint", intArray));
                    statement.setArray(7, connection.createArrayOf("text", stringArray));
                } else {
                    throw new RuntimeException("Different number of tag_id and tag_name parameters");
                }
            } else if (!nonNull(petDataMap.get("tags_id")) && !nonNull(petDataMap.get("tags_name"))) {
                statement.setArray(6, connection.createArrayOf("bigint", new Integer[0]));
                statement.setArray(7, connection.createArrayOf("text", new String[0]));
            } else {
                throw new RuntimeException("Different number of tag_id and tag_name parameters");
            }
            statement.setString(8, petDataMap.get("status"));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertPetsIntoDatabase(Pet[] pets) {
        Arrays.stream(pets).forEach(this::insertPetIntoDatabase);
    }

    public void insertPetsIntoDatabase(List<Pet> pets) {
        pets.forEach(this::insertPetIntoDatabase);
    }

    public void deletePetsByName(String name) {
        try(Connection connection = factory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_PET_BY_NAME_QUERY);
            statement.setString(1, name);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pet> getPetByName(String name) {
        List<Pet> pets = new ArrayList<>();
        try(Connection connection = factory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_PET_BY_NAME_QUERY);
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    pets.add(mapResultSetIntoPet(resultSet));
                }
            };

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pets;
    }

    public List<Pet> getPetByPetId(long id) {
        List<Pet> pets = new ArrayList<>();
        try(Connection connection = factory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_PET_BY_PET_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pets.add(mapResultSetIntoPet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pets;
    }

    public void deletePetByPetId(long id) {
        try (Connection connection = factory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_PET_BY_PET_ID_QUERY);
            statement.setLong(1, id);
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        private Pet mapResultSetIntoPet(ResultSet resultSet) {
        try {
            Pet pet = new Pet();
            pet = Pet.builder()
                    .id(resultSet.getLong("id"))
                    .category(new Category(resultSet.getLong("category_id"),
                            resultSet.getString("category_name")))
                    .name(resultSet.getString("name"))
                    .photoUrls(ResultSetHelper.getResultSetArrayAsStringArray(resultSet, "photourls"))
                    .status(resultSet.getString("status"))
                    .tags(mapResultSetIntoTags(resultSet))
                    .build();
            System.out.println(pet);
            return pet;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Tag[] mapResultSetIntoTags(ResultSet resultSet) {
            long[] tagIds = ResultSetHelper.getResultSetAsLongArray(resultSet, "tags_id");
            String[] tagsName = ResultSetHelper.getResultSetArrayAsStringArray(resultSet, "tags_name");
            Tag[] tags = new Tag[tagIds.length];
            return IntStream.range(0, tagIds.length)
                .mapToObj(i -> new Tag(tagIds[i], tagsName[i]))
                .toArray(Tag[]::new);
    }
}
