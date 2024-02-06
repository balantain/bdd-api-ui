package com.epam.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseError {
    private int code;
    private String type;
    private String message;
}
