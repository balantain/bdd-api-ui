package com.epam.utils;

import java.io.File;
import java.util.Objects;

public class FileHelpers {
    /**
     * This class is used to get file from resourses
     */
    private FileHelpers() {
    }

    public static File getFileFromResource(String fileName) {
        return new File(Objects.requireNonNull(FileHelpers.class.getClassLoader().getResource(fileName)).getFile());
    }
}
