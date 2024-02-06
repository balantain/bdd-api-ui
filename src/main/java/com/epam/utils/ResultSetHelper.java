package com.epam.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ResultSetHelper {

    public static String[] getResultSetArrayAsStringArray(ResultSet resultSet, String columnName) {
        try{
            String resultString = resultSet.getString(columnName);
            if (resultString != null ) {
                return resultSet.getString(columnName).replaceAll("[{]", "")
                        .replaceAll("[}]", "").trim().split(",");
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] getResultSetAsIntArray(ResultSet resultSet, String columnName) {
        return Arrays.stream(getResultSetArrayAsStringArray(resultSet, columnName)).mapToInt(Integer::parseInt).toArray();
    }

    public static long[] getResultSetAsLongArray(ResultSet resultSet, String columnName) {
        return Arrays.stream(getResultSetArrayAsStringArray(resultSet, columnName)).mapToLong(Long::parseLong).toArray();
    }
}
