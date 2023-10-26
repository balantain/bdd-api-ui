package com.epam.api.models;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

public class ModelBuilder {

    /**
     * This method builds List<Pet> using data from cucumber file
     */
    public static List<Pet> buildPetWithData(List<Map<String, String>> pets) {
        List<Pet> petsList = new ArrayList<>();
        pets.forEach(petDataMap -> {
            Pet pet = new Pet();
            Category category = new Category();
            Tag[] tags;
            if (nonNull(petDataMap.get("pet_id"))) pet.setId(Long.parseLong(petDataMap.get("pet_id")));
            if (nonNull(petDataMap.get("category_id"))) category.setId(Long.parseLong(petDataMap.get("category_id")));
            category.setName(petDataMap.get("category_name"));
            pet.setCategory(category);
            if (nonNull(petDataMap.get("name"))) pet.setName(petDataMap.get("name"));
            if (nonNull(petDataMap.get("photourls"))) pet.setPhotoUrls(petDataMap.get("photourls").split(","));
            if (nonNull(petDataMap.get("status"))) pet.setStatus(petDataMap.get("status"));
            if (nonNull(petDataMap.get("tags_id")) && (nonNull(petDataMap.get("tags_name")))) {
                    var intArray = Arrays.stream(petDataMap.get("tags_id").split(",")).mapToInt(Integer::parseInt).toArray();
                    var stringArray = petDataMap.get("tags_name").split(",");
                    if(intArray.length == stringArray.length) {
                        List<Tag> tagList = new ArrayList<>();
                        IntStream.range(0, intArray.length).forEach(i -> {
                            Tag tag = new Tag();
                            tag.setId(intArray[i]);
                            tag.setName(stringArray[i]);
                            tagList.add(tag);
                        });
                        tags = tagList.toArray(new Tag[0]);
                        pet.setTags(tags);
                    }
            } else if (!nonNull(petDataMap.get("tags_id")) && nonNull(petDataMap.get("tags_name"))){
                        throw new RuntimeException("Different number of tag_id and tag_name parameters");
            } else if (nonNull(petDataMap.get("tags_id")) && !nonNull(petDataMap.get("tags_name"))){
                throw new RuntimeException("Different number of tag_id and tag_name parameters");
            }
            petsList.add(pet);
        });
        return petsList;
    }
}
