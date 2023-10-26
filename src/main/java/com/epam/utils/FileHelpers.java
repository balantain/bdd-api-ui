package com.epam.utils;

import java.io.File;
import java.util.Objects;

public class FileHelpers {
    /**
     * This class is used to get file from resourses
     */

    private static final String CONFIG_FOLDER = "environment";
    private static final String JSON_SCHEMA_FOLDER = "schemas";
    private FileHelpers() {
    }

    public static File getPropertyFromResourse(String propertyFileName) {
        return FileHelpers.getFileFromResource("%s/%s.properties"
                .formatted(CONFIG_FOLDER, propertyFileName));
    }

    public static File getJsonSchemaFromResource(String schemaName) {
        return getFileFromResource("%s/%s.json"
                .formatted(JSON_SCHEMA_FOLDER, schemaName));
    }

    private static File getFileFromResource(String fileName) {
        return new File(Objects.requireNonNull(FileHelpers.class.getClassLoader().getResource(fileName)).getFile());
    }

//    uncomment, if want to run tests locally
//    public static File getFileFromResource(String fileName) {
//        return new File(Objects.requireNonNull("src/test/resources/%s".formatted(fileName)));
//    }
}
