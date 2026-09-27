package com.automatizacion.base.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FrameworkConfigTest {

    @AfterEach
    void cleanUp() {
        System.clearProperty("env");
        FrameworkConfig.reset();
    }

    @Test
    void shouldLoadLocalEnvironmentByDefault() {
        FrameworkConfig config = FrameworkConfig.getInstance();

        Assertions.assertEquals("local", config.getActiveEnv());
        Assertions.assertEquals("Android", config.get("platform.name"));
        Assertions.assertFalse(config.get("appium.server.url").isBlank());
    }

    @Test
    void shouldResolveOptionalPropertyWithDefault() {
        FrameworkConfig config = FrameworkConfig.getInstance();

        Assertions.assertEquals("120", config.getOrDefault("new.command.timeout", "60"));
    }
}
