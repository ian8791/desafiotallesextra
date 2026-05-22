package com.biblioteca.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
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
        boolean loaded = false;

        // Try to load .env from classpath (src/main/resources)
        try (InputStream input = EnvUtil.class.getClassLoader().getResourceAsStream(".env")) {
            if (input != null) {
                props.load(input);
                loaded = true;
                System.out.println("EnvUtil: carregou .env do classpath");
            }
        } catch (IOException e) {
            // ignore and try other sources
        }

        if (!loaded) {
            java.io.File envFile = new java.io.File(".env");
            if (envFile.exists() && envFile.canRead()) {
                try (InputStream input2 = new java.io.FileInputStream(envFile)) {
                    props.load(input2);
                    loaded = true;
                    System.out.println("EnvUtil: carregou .env do diretório de trabalho");
                } catch (IOException e) {
                    // ignore and try fallback
                }
            }
        }

        if (!loaded) {
            try (InputStream example = EnvUtil.class.getClassLoader().getResourceAsStream(".env.example")) {
                if (example != null) {
                    props.load(example);
                    System.out.println("EnvUtil: carregou .env.example como fallback");
                }
            } catch (IOException e) {
                // ignore fallback load errors
            }
        }

        Map<String, String> systemEnv = System.getenv();
        for (String key : systemEnv.keySet()) {
            if (key.startsWith("DB_")) {
                props.setProperty(key, systemEnv.get(key));
            }
        }

        if (props.containsKey("DB_USER")) {
            System.out.println("EnvUtil: DB_USER=" + props.getProperty("DB_USER") + ", DB_NAME=" + props.getProperty("DB_NAME") + ", DB_HOST=" + props.getProperty("DB_HOST"));
        }

        return props;
    }
}