package com.automatizacion.base.steps;

import com.automatizacion.base.config.FrameworkConfig;
import com.automatizacion.base.driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.appmanagement.ApplicationState;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobileSmokeSteps {

    private static final Logger log = LoggerFactory.getLogger(MobileSmokeSteps.class);

    private ApplicationState appState;

    @Given("mobile execution is enabled")
    public void ejecucionMobileHabilitada() {
        boolean enabled = Boolean.parseBoolean(System.getProperty("run.mobile.tests", "true"));
        log.info("mobile execution enabled={}", enabled);
        Assumptions.assumeTrue(enabled, "Mobile scenario skipped because run.mobile.tests=false");
    }

    @When("I initialize the Appium driver")
    public void inicializoDriverAppium() {
        Assertions.assertNotNull(DriverManager.getDriver(), "Driver was not initialized by Hooks");
        log.info("driver initialized");
    }

    @Then("the mobile session should be available")
    public void sesionMobileDisponible() {
        Assertions.assertNotNull(DriverManager.getDriver().getSessionId(), "Appium sessionId was not created");
        log.info("sessionId={}", DriverManager.getDriver().getSessionId());
    }

    @When("I query the app launch state")
    public void consultoEstadoLanzamientoApp() {
        AppiumDriver driver = DriverManager.getDriver();
        Assertions.assertNotNull(driver, "Driver was not initialized by Hooks");

        FrameworkConfig config = FrameworkConfig.getInstance();
        String platform = config.get("platform.name").toLowerCase();
        String appId = "android".equals(platform)
            ? config.getOrDefault("app.package", "")
            : config.getOrDefault("bundle.id", "");

        Assertions.assertFalse(appId.isBlank(), "App identifier is empty. Configure app.package (Android) or bundle.id (iOS)");
        appState = ((InteractsWithApps) driver).queryAppState(appId);

        log.info("appId={} appState={}", appId, appState);
    }

    @Then("the app should be running in foreground")
    public void appCorriendoEnForeground() {
        Assertions.assertNotNull(appState, "App state was not queried. Execute step: I query the app launch state");
        Assertions.assertEquals(
            ApplicationState.RUNNING_IN_FOREGROUND,
            appState,
            "Expected app in foreground, but state was: " + appState
        );
        log.info("app is visible in device (foreground)");
    }
}
