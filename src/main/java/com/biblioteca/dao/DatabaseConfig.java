package com.biblioteca.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.biblioteca.util.EnvUtil;

public class DatabaseConfig {
    private static Connection connection;

    public static Connection getConnection() throws IOException, SQLException {
        if (connection != null) return connection;

        // Load properties from .env via EnvUtil
        Properties props = EnvUtil.loadEnv();

        String url = "jdbc:mysql://" + props.getProperty("DB_HOST") + ":" + props.getProperty("DB_PORT") + "/" + props.getProperty("DB_NAME");
        String user = props.getProperty("DB_USER");
        String password = props.getProperty("DB_PASS");

        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}