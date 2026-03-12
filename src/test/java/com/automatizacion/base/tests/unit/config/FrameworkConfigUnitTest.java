package com.automatizacion.base.tests.unit.config;

import com.automatizacion.base.config.FrameworkConfig;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FrameworkConfigUnitTest {

    @Test
    void shouldLoadLocalEnvironmentByDefault() {
        System.clearProperty("env");
        System.clearProperty("platform.name");
        FrameworkConfig.reset();

        FrameworkConfig config = FrameworkConfig.getInstance();

        Assertions.assertEquals("local", config.getActiveEnv());
        Assertions.assertFalse(config.get("platform.name").isBlank());
    }

    @Test
    void shouldAllowSystemPropertyOverrides() {
        System.setProperty("env", "local");
        System.setProperty("platform.name", "iOS");
        System.setProperty("device.name", "iPhone 16 Pro");
        FrameworkConfig.reset();

        try {
            FrameworkConfig config = FrameworkConfig.getInstance();

            Assertions.assertEquals("local", config.getActiveEnv());
            Assertions.assertEquals("iOS", config.get("platform.name"));
            Assertions.assertEquals("iPhone 16 Pro", config.get("device.name"));
        } finally {
            System.clearProperty("env");
            System.clearProperty("platform.name");
            System.clearProperty("device.name");
            FrameworkConfig.reset();
        }
    }

    @Test
    void shouldLoadBrowserstackEnvironmentFile() {
        System.setProperty("env", "browserstack");
        FrameworkConfig.reset();

        try {
            FrameworkConfig config = FrameworkConfig.getInstance();

            Assertions.assertEquals("browserstack", config.getActiveEnv());
            Assertions.assertFalse(config.get("appium.server.url").isBlank());
            Assertions.assertFalse(config.get("device.name").isBlank());
        } finally {
            System.clearProperty("env");
            FrameworkConfig.reset();
        }
    }
}



