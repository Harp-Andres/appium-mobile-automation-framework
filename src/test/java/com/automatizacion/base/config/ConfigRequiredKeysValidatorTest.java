package com.automatizacion.base.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

class ConfigRequiredKeysValidatorTest {

    @Test
    void shouldPassWhenAllRequiredKeysPresent() {
        Properties props = new Properties();
        for (String key : ConfigRequiredKeysValidator.REQUIRED_KEYS) {
            props.setProperty(key, "value");
        }

        ConfigRequiredKeysValidator.validate(props);
    }

    @Test
    void shouldFailWhenRequiredKeyMissing() {
        Properties props = new Properties();
        props.setProperty("appium.server.url", "http://127.0.0.1:4723");

        IllegalStateException ex = Assertions.assertThrows(
            IllegalStateException.class,
            () -> ConfigRequiredKeysValidator.validate(props)
        );
        Assertions.assertTrue(ex.getMessage().contains("platform.name"));
    }
}
