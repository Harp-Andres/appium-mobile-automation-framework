package com.automatizacion.base.config;

import java.util.Objects;
import java.util.Properties;

/**
 * Valida claves obligatorias del archivo de entorno local (emulador/dispositivo).
 */
public final class ConfigRequiredKeysValidator {

    static final String[] REQUIRED_KEYS = {
        "appium.server.url",
        "platform.name",
        "device.name",
        "automation.name"
    };

    private ConfigRequiredKeysValidator() {
    }

    public static void validate(Properties loaded) {
        Objects.requireNonNull(loaded, "properties");
        for (String key : REQUIRED_KEYS) {
            String value = loaded.getProperty(key);
            if (value == null || value.isBlank()) {
                throw new IllegalStateException("Falta propiedad obligatoria: " + key);
            }
        }
    }
}
