package com.epam.api.enums;

import lombok.Getter;

@Getter
public enum PetStatus {
    AVAILABLE("available"), PENDING("pending"), SOLD("sold");

    private final String status;

    PetStatus(String status) {
        this.status = status;
    }
}
