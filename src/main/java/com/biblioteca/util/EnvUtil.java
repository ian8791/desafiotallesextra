package com.biblioteca.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvUtil {
    /**
     * Load environment variables from .env file located in the classpath.
     * The .env file should contain key=value pairs, one per line.
     *
     * @return Properties object with loaded environment variables.
     * @throws IOException if the .env file cannot be read.
     */
    public static Properties loadEnv() throws IOException {
        Properties props = new Properties();
        // Try to load .env from classpath (src/main/resources)
        try (InputStream input = EnvUtil.class.getClassLoader().getResourceAsStream(".env")) {
            if (input != null) {
                props.load(input);
            } else {
                // If not found in classpath, try to load from working directory
                java.io.File envFile = new java.io.File(".env");
                if (envFile.exists() && envFile.canRead()) {
                    try (InputStream input2 = new java.io.FileInputStream(envFile)) {
                        props.load(input2);
                    }
                }
            }
        } catch (IOException e) {
            // If no .env found, fall back to default values (will be overridden by actual .env)
            // This allows the app to start without .env for quick testing.
        }
        return props;
    }
}