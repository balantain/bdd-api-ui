package com.epam.api.models;

import java.util.*;
import java.util.stream.IntStream;

import static io.netty.util.internal.StringUtil.EMPTY_STRING;

public class ModelBuilder {

    public static List<Pet> buildPetWithData(List<Map<String, String>> pets) {
        List<Pet> petsList = new ArrayList<>();
        pets.forEach(petDataMap -> {
//            save parameters into strings
            var petId = getOptionalValueFromListOfMaps(petDataMap, "pet_id");
            var categoryId = getOptionalValueFromListOfMaps(petDataMap,"category_id");
            var categoryName = getOptionalValueFromListOfMaps(petDataMap,"category_id");
            var name = getOptionalValueFromListOfMaps(petDataMap,"name");
            var photoUrls = getOptionalValueFromListOfMaps(petDataMap,"photourls");
            var tagsId = getOptionalValueFromListOfMaps(petDataMap,"tags_id");
            var tagsName = getOptionalValueFromListOfMaps(petDataMap,"tags_name");
            var status = getOptionalValueFromListOfMaps(petDataMap,"status");
//            create empty models;
            Pet pet = new Pet();
            Category category = new Category();
            Tag[] tags;
//            Parse parameters
            if (petId.isBlank() && name.isBlank()) {
                throw new RuntimeException("pet_id, name, photourls are obvious parameters");
            }
            pet.setId(Long.parseLong(petId));
            pet.setName(name);
            if (!photoUrls.isBlank()) pet.setPhotoUrls(photoUrls.split(","));
            else pet.setPhotoUrls(new String[0]);
            if (!categoryId.isBlank()) category.setId(Integer.parseInt(categoryId));
            if (!categoryName.isBlank()) category.setName(categoryName);
            pet.setCategory(category);
            if (!tagsId.isBlank()) {
                if (!tagsName.isBlank()) {
                    if (tagsId.split(",").length == tagsName.split(",").length) {
                        int[] tagIds = Arrays.stream(tagsId.split(",")).mapToInt(Integer::parseInt).toArray();
                        String[] tagNames = tagsName.split(",");
                        List<Tag> tagList = new ArrayList<>();
                        IntStream.range(0, tagIds.length).forEach(i -> {
                            Tag tag = new Tag();
                            tag.setId(tagIds[i]);
                            tag.setName(tagNames[i]);
                            tagList.add(tag);
                        });
                        tags = tagList.toArray(new Tag[0]);
                        pet.setTags(tags);
                    } else {
                        throw new RuntimeException("Different number of tag_id and tag name parameters");
                    }
                }
            } else if (!tagsName.isBlank()) {
                throw new RuntimeException("Different number of tag_id and tag name parameters");
            } else {
                tags = new Tag[0];
                pet.setTags(tags);
            }
            if (!status.isBlank()) pet.setStatus(status);
            petsList.add(pet);
        });
        return petsList;
    }

    private static String getOptionalValueFromListOfMaps(Map<String, String> map, String key) {
        if (Objects.nonNull(map.get(key))){
            return map.getOrDefault(key, EMPTY_STRING);
        }
        else return EMPTY_STRING;
    }
}
