package com.epam.utils;

import com.epam.api.enums.InfoType;

public class PathBuilder {

    public static final String FIND_BY_STATUS = "/findByStatus";

    public static String getPath(InfoType type, String... args) {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(type.getEndpoint());
        for (String arg : args) {
            if (arg != null && !arg.isEmpty()) {
                pathBuilder.append(arg);
            }
        }

        return pathBuilder.toString();
    }
}
