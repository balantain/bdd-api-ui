package com.epam.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.util.Objects.nonNull;

public class SQLDataBaseUtils {

    /**
     * This method is used to prevent NullPointerException while trying to parse NULL value
     * into Long, which may gcome from Cucumber feature file
     */
    public static void setLongIntoStatement(PreparedStatement statement, int index, String value) throws SQLException {
        if(nonNull(value)) {
            statement.setLong(index, Long.parseLong(value));
        } else {
            statement.setNull(index, -5);
        }
    }

    /**
     * This methods are used to prevent NullPointerException while trying to parse NULL value
     * into LONG, which may gcome from Cucumber feature file and can use default value instead
     */
    public static void setLongIntoStatement(PreparedStatement statement, int index, String value, String defaultValue) throws SQLException {
        if(nonNull(value)) {
            statement.setLong(index, Long.parseLong(value));
        } else if (!defaultValue.isBlank()) {
            statement.setLong(index, Long.parseLong(defaultValue));
        } else {
            statement.setNull(index, -5);
        }
    }

    public static void setLongIntoStatement(PreparedStatement statement, int index, String value, Long defaultValue) throws SQLException {
        if(nonNull(value)) {
            statement.setLong(index, Long.parseLong(value));
        } else {
            statement.setLong(index, defaultValue);
        }
    }

    /**
     * This method is used to prevent NullPointerException while trying to parse NULL value
     * into INTEGER, which may gcome from Cucumber feature file
     */
    public static void setIntIntoStatement(PreparedStatement statement, int index, String value) throws SQLException {
        if(nonNull(value)) {
            statement.setInt(index, Integer.parseInt(value));
        } else {
            statement.setNull(index, -5);
        }
    }

    /**
     * This methods are used to prevent NullPointerException while trying to parse NULL value
     * into INTEGER, which may gcome from Cucumber feature file and can use default value instead
     */
    public static void setIntIntoStatement(PreparedStatement statement, int index, String value, String defaultValue) throws SQLException {
        if(nonNull(value)) {
            statement.setInt(index, Integer.parseInt(value));
        } else if (nonNull(defaultValue)) {
            statement.setInt(index, Integer.parseInt(defaultValue));
        } else {
            statement.setNull(index, -5);
        }
    }

    public static void setIntIntoStatement(PreparedStatement statement, int index, String value, int defaultValue) throws SQLException {
        if(nonNull(value)) {
            statement.setInt(index, Integer.parseInt(value));
        } else {
            statement.setInt(index, defaultValue);
        }
    }

    /**
     * This method is used to insert default value instead of NULL value, which may come from Cucumber feature file
     */
    public static void setStringIntoStatement(PreparedStatement statement, int index, String value, String defaultValue) throws SQLException {
        if(nonNull(value)) {
            statement.setString(index, value);
        } else {
            statement.setString(index, defaultValue);
        }
    }

    /**
     * This method is used to insert default value instead of NULL value, which may come from Cucumber feature file
     */
    public static void setBigintArrayIntoStatement(Connection connection, PreparedStatement statement, int index, String value, Integer[] defaultValue) throws SQLException {
        setArrayIntoStatement(connection, statement, index, value, "bigint", defaultValue);
    }

    /**
     * This method is used to insert default value instead of NULL value, which may come from Cucumber feature file
     */
    public static void setTextArrayIntoStatement(Connection connection, PreparedStatement statement, int index, String value, String[] defaultValue) throws SQLException {
        setArrayIntoStatement(connection, statement, index, value, "text", defaultValue);
    }

    private static void setArrayIntoStatement(Connection connection, PreparedStatement statement, int index, String value, String arrayType, Object[] defaultValue) throws SQLException {
        if (nonNull(value)) {
            statement.setArray(index, connection.createArrayOf(arrayType, value.split(",")));
        } else {
            statement.setArray(index, connection.createArrayOf(arrayType, defaultValue));
        }
    }
}
