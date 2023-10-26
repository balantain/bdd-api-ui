package com.epam;

import com.epam.api.models.Pet;

public class Main {
    public static void main(String[] args) {
        Pet pet = Pet.builder().build();
        System.out.println(pet);
    }
}
