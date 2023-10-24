package com.epam.configuration.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Environment {
    @JsonProperty("api.base.uri")
    private String apiBaseUri;

    @JsonProperty("postgres.base.uri")
    private String postgresBaseUri;

    @JsonProperty("postgres.username")
    private String postgresUsername;

    @JsonProperty("postgres.password")
    private String postgresPassword;
}
