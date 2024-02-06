package com.epam.api.configuration;

import com.epam.api.filters.FilterManager;
import com.epam.configuration.TestConfig;
import io.restassured.RestAssured;
import io.restassured.config.*;
import io.restassured.filter.Filter;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RestConfig {
    /**
     * This class is used for base RestAssured configuration
     */

    public static RequestSpecification getConfig(Filter... filters) {
        RestAssuredConfig config = RestAssured.config()
                .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset(UTF_8))
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                        .defaultObjectMapperType(ObjectMapperType.JACKSON_2))
                .logConfig(LogConfig.logConfig().enablePrettyPrinting(true))
                .httpClient(new HttpClientConfig().setParam(
                        "CONNECTION_MANAGER_TIMEOUT",
                        3000));
        var specification = RestAssured.given()
                .config(config)
                .baseUri(TestConfig.getEnv().getApiBaseUri())
                .contentType(ContentType.JSON)
                .filter(FilterManager.getAllureLoggingFilter());

        for (Filter filter : filters) {
            specification.filter(filter);
        }
        return specification;
    }
}
