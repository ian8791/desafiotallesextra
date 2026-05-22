package com.biblioteca.dao;

import com.biblioteca.util.EnvUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JpaUtil {
    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() throws IOException {
        if (emf == null) {
            Properties props = EnvUtil.loadEnv();
            String host = props.getProperty("DB_HOST", "localhost");
            String port = props.getProperty("DB_PORT", "3306");
            String name = props.getProperty("DB_NAME", "biblioteca_db");
            String user = props.getProperty("DB_USER", "root");
            String pass = props.getProperty("DB_PASS", "");

            createDatabaseIfNotExists(host, port, name, user, pass);

            String url = "jdbc:mysql://" + host + ":" + port + "/" + name + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

            System.out.println("JpaUtil: tentando conectar em " + url + " com usuário " + user);

            Map<String, String> cfg = new HashMap<>();
            cfg.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
            cfg.put("jakarta.persistence.jdbc.url", url);
            cfg.put("jakarta.persistence.jdbc.user", user);
            cfg.put("jakarta.persistence.jdbc.password", pass);
            cfg.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            cfg.put("hibernate.hbm2ddl.auto", "update");
            cfg.put("hibernate.show_sql", "true");

            emf = Persistence.createEntityManagerFactory("bibliotecaPU", cfg);
        }
        return emf.createEntityManager();
    }

    private static void createDatabaseIfNotExists(String host, String port, String name, String user, String pass) {
        String url = "jdbc:mysql://" + host + ":" + port + "/?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + name + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            System.out.println("JpaUtil: banco de dados '" + name + "' verificado/criado.");
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível criar/verificar o banco de dados: " + e.getMessage(), e);
        }
    }

    public static void close() {
        if (emf != null && emf.isOpen()) emf.close();
    }
}
