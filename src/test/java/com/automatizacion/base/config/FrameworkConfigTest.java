package com.automatizacion.base.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FrameworkConfigTest {

    @Test
    void shouldLoadLocalEnvironmentByDefault() {
        System.clearProperty("env");
        FrameworkConfig.reset();

        FrameworkConfig config = FrameworkConfig.getInstance();

        Assertions.assertEquals("local", config.getActiveEnv());
        Assertions.assertFalse(config.get("platform.name").isBlank());
    }
}

