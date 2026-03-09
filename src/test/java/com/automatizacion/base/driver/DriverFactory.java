package com.automatizacion.base.driver;

import com.automatizacion.base.config.FrameworkConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static AppiumDriver createDriver() {
        FrameworkConfig config = FrameworkConfig.getInstance();
        String platformName = config.get("platform.name").toLowerCase();
        URL serverUrl = buildServerUrl(config.get("appium.server.url"));
        long startTime = System.currentTimeMillis();

        logInfo("createDriver start env=" + config.getActiveEnv() + " platform=" + platformName + " serverUrl=" + serverUrl);

        AppiumDriver driver;
        switch (platformName) {
            case "android" -> driver = new AndroidDriver(serverUrl, buildAndroidOptions(config));
            case "ios" -> driver = new IOSDriver(serverUrl, buildIosOptions(config));
            default -> throw new IllegalArgumentException("platform.name no soportado: " + platformName);
        }

        String timeout = config.getOrDefault("new.command.timeout", "120");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        System.setProperty("new.command.timeout", timeout);

        long elapsed = System.currentTimeMillis() - startTime;
        logInfo("createDriver success sessionId=" + driver.getSessionId() + " elapsedMs=" + elapsed);
        return driver;
    }

    private static UiAutomator2Options buildAndroidOptions(FrameworkConfig config) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        String platformVersion = config.getOrDefault("platform.version", "");
        if (!platformVersion.isBlank()) {
            options.setPlatformVersion(platformVersion);
        }
        options.setDeviceName(config.get("device.name"));
        options.setAutomationName(config.get("automation.name"));

        String appPath = config.getOrDefault("app.path", "");
        if (!appPath.isBlank()) {
            Path path = Path.of(appPath);
            if (!Files.exists(path)) {
                throw new IllegalStateException("No existe app.path: " + path.toAbsolutePath());
            }
            options.setApp(path.toAbsolutePath().toString());
            logInfo("Android options appStrategy=appPath appPath=" + path.toAbsolutePath());
        } else {
            String appPackage = config.getOrDefault("app.package", "");
            String appActivity = config.getOrDefault("app.activity", "");
            if (appPackage.isBlank() || appActivity.isBlank()) {
                throw new IllegalStateException("Debes definir app.path o app.package + app.activity para Android");
            }
            options.setAppPackage(appPackage);
            options.setAppActivity(appActivity);
            logInfo("Android options appStrategy=packageActivity appPackage=" + appPackage + " appActivity=" + appActivity);
        }

        logInfo("Android options deviceName=" + options.getDeviceName() + " platformVersion="
            + (platformVersion.isBlank() ? "<default>" : platformVersion));
        return options;
    }

    private static XCUITestOptions buildIosOptions(FrameworkConfig config) {
        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName("iOS");
        String platformVersion = config.getOrDefault("platform.version", "");
        if (!platformVersion.isBlank()) {
            options.setPlatformVersion(platformVersion);
        }
        options.setDeviceName(config.get("device.name"));
        options.setAutomationName(config.get("automation.name"));

        String appPath = config.getOrDefault("app.path", "");
        String bundleId = config.getOrDefault("bundle.id", "");
        if (!appPath.isBlank()) {
            Path path = Path.of(appPath);
            if (!Files.exists(path)) {
                throw new IllegalStateException("No existe app.path: " + path.toAbsolutePath());
            }
            options.setApp(path.toAbsolutePath().toString());
            logInfo("iOS options appStrategy=appPath appPath=" + path.toAbsolutePath());
        } else if (!bundleId.isBlank()) {
            options.setBundleId(bundleId);
            logInfo("iOS options appStrategy=bundleId bundleId=" + bundleId);
        } else {
            throw new IllegalStateException("Debes definir app.path o bundle.id para iOS");
        }

        return options;
    }

    private static URL buildServerUrl(String rawUrl) {
        try {
            return new URL(rawUrl);
        } catch (MalformedURLException exception) {
            logError("appium.server.url invalido=" + rawUrl);
            throw new IllegalArgumentException("appium.server.url invalido: " + rawUrl, exception);
        }
    }

    private static void logInfo(String message) {
        System.out.println("[DRIVER][INFO] " + message);
    }

    private static void logError(String message) {
        System.out.println("[DRIVER][ERROR] " + message);
    }
}
