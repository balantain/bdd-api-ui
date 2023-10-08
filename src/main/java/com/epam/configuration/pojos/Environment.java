package com.epam.configuration.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Environment {
    @JsonProperty("api.base.uri")
    private String apiBaseUri;
}
