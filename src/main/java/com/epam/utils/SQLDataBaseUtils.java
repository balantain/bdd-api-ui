package com.epam.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.util.Objects.nonNull;

public class SQLDataBaseUtils {

    public static void setIntIntoStatement(PreparedStatement statement, int index, String value, String defaultValue) throws SQLException {
        if(nonNull(value)) {
            statement.setInt(index, Integer.parseInt(value));
        } else if (nonNull(defaultValue)) {
            statement.setInt(index, Integer.parseInt(defaultValue));
        } else {
            statement.setNull(index, -5);
        }
    }

    public static void setIntIntoStatement(PreparedStatement statement, int index, String value) throws SQLException {
        if(nonNull(value)) {
            statement.setInt(index, Integer.parseInt(value));
        } else {
            statement.setNull(index, -5);
        }
    }

    public static void setLongIntoStatement(PreparedStatement statement, int index, String value, String defaultValue) throws SQLException {
        if(nonNull(value)) {
            statement.setLong(index, Long.parseLong(value));
        } else if (nonNull(defaultValue)) {
            statement.setInt(index, Integer.parseInt(defaultValue));
        } else {
            statement.setNull(index, -5);
        }
    }

    public static void setLongIntoStatement(PreparedStatement statement, int index, String value) throws SQLException {
        if(nonNull(value)) {
            statement.setLong(index, Long.parseLong(value));
        } else {
            statement.setNull(index, -5);
        }
    }

}
