package com.epam.api.enums;

import lombok.Getter;

@Getter
public enum InfoType {
    PET("/pet"), STORE("/store"), USER("/user");

    private final String endpoint;

    InfoType(String endpoint) {
        this.endpoint = endpoint;
    }
}
