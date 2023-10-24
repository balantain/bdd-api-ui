package com.epam.database.configuration;


import com.epam.configuration.TestConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionFactory implements ConnectionFactory {
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(TestConfig.getEnv().getPostgresBaseUri(),
                TestConfig.getEnv().getPostgresUsername(), TestConfig.getEnv().getPostgresPassword());
    }
}
