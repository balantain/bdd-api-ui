package com.epam.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;

import java.io.File;
import java.io.IOException;

public class PropertyConfigurationReader {
    /**
     * This class is used to read properties, saved into property files and map them into Pojo classes
     */

    private PropertyConfigurationReader() {
    }

    public static <T> T readPropertyConfig(File file, Class<T> clazz){
        JavaPropsSchema schema = JavaPropsSchema.emptySchema().withoutPathSeparator();
        var objectMapper = new ObjectMapper(new JavaPropsFactory());
        T mappedObject;
        try {
            mappedObject = objectMapper.reader(schema).readValue(file, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't map property file %s into %s object \n%s"
                    .formatted(file, clazz.getName(), e.getMessage()));
        }
        return mappedObject;
    }
}
