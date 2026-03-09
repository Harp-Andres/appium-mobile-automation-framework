package com.automatizacion.base.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

public final class FrameworkConfig {

    private static final String DEFAULT_ENV = "local";
    private static final String ENV_PROPERTY = "env";
    private static final String CONFIG_BASE_PATH = "config";

    private static FrameworkConfig instance;

    private final String activeEnv;
    private final Properties properties;

    private FrameworkConfig() {
        this.activeEnv = System.getProperty(ENV_PROPERTY, DEFAULT_ENV).trim();
        this.properties = load(activeEnv);
    }

    public static synchronized FrameworkConfig getInstance() {
        if (instance == null) {
            instance = new FrameworkConfig();
        }
        return instance;
    }

    public static synchronized void reset() {
        instance = null;
    }

    public String getActiveEnv() {
        return activeEnv;
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("No se encontro la propiedad obligatoria: " + key);
        }
        return value.trim();
    }

    public String getOrDefault(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue).trim();
    }

    private Properties load(String env) {
        String fileName = env + ".properties";
        Path path = Path.of("src", "test", "resources", CONFIG_BASE_PATH, fileName);

        if (!Files.exists(path)) {
            throw new IllegalStateException("No existe archivo de configuracion: " + path.toAbsolutePath());
        }

        Properties loaded = new Properties();
        try (InputStream inputStream = Files.newInputStream(path)) {
            loaded.load(inputStream);
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo cargar configuracion para env=" + env, exception);
        }

        validateRequiredKeys(loaded);
        return loaded;
    }

    private void validateRequiredKeys(Properties loaded) {
        Objects.requireNonNull(loaded, "properties");
        String[] required = {
            "appium.server.url",
            "platform.name",
            "device.name",
            "automation.name"
        };

        for (String key : required) {
            String value = loaded.getProperty(key);
            if (value == null || value.isBlank()) {
                throw new IllegalStateException("Falta propiedad obligatoria: " + key);
            }
        }
    }
}

