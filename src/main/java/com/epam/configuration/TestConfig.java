package com.epam.configuration;

import com.epam.configuration.pojos.Environment;
import com.epam.utils.FileHelpers;
import lombok.Getter;

public class TestConfig {
    /**
     * This class is used as entry point to store configuration
     */
    private static final String ENVIRONMENT = System.getProperty("env", "qa");
    @Getter
    private static final Environment env;

    private TestConfig() {
    }

    static {
        env = PropertyConfigurationReader
                .readPropertyConfig(FileHelpers.getPropertyFromResourse(ENVIRONMENT), Environment.class);
    }
}
