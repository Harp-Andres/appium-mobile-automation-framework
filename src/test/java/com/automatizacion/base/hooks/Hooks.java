package com.automatizacion.base.hooks;

import com.automatizacion.base.driver.DriverFactory;
import com.automatizacion.base.driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assumptions;

public class Hooks {

    @Before(order = 1, value = "@mobile")
    public void beforeMobileScenario() {
        String runMobileRaw = System.getProperty("run.mobile.tests", "true");
        String filterTags = System.getProperty("cucumber.filter.tags", "<not-set>");
        String env = System.getProperty("env", "local");
        boolean runMobile = Boolean.parseBoolean(runMobileRaw);

        logInfo("beforeMobileScenario start env=" + env + " run.mobile.tests=" + runMobileRaw
            + " cucumber.filter.tags=" + filterTags + " thread=" + Thread.currentThread().getName());

        if (!runMobile) {
            logWarn("Mobile deshabilitado por propiedad run.mobile.tests=false");
        }
        Assumptions.assumeTrue(runMobile, "Pruebas mobile deshabilitadas. Usa -Drun.mobile.tests=false para omitirlas.");

        try {
            AppiumDriver driver = DriverFactory.createDriver();
            DriverManager.setDriver(driver);
            logInfo("Sesion Appium creada sessionId=" + driver.getSessionId());
        } catch (RuntimeException exception) {
            logError("Fallo al crear driver Appium: " + exception.getMessage());
            throw exception;
        }
    }

    @After(order = 1)
    public void attachFailureDetails(Scenario scenario) {
        if (!scenario.isFailed()) {
            return;
        }

        logWarn("Escenario fallido name=" + scenario.getName() + " tags=" + String.join(",", scenario.getSourceTagNames()));
        Allure.addAttachment("scenario-name", "text/plain", scenario.getName(), ".txt");

        String tags = String.join(",", scenario.getSourceTagNames());
        Allure.addAttachment("scenario-tags", "text/plain", tags, ".txt");
    }

    @After(order = 0, value = "@mobile")
    public void afterMobileScenario() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            logWarn("afterMobileScenario sin driver en DriverManager");
        } else {
            logInfo("Cerrando sesion Appium sessionId=" + driver.getSessionId());
        }
        DriverManager.quitDriver();
        logInfo("afterMobileScenario end");
    }

    private static void logInfo(String message) {
        System.out.println("[FRAMEWORK][INFO] " + message);
    }

    private static void logWarn(String message) {
        System.out.println("[FRAMEWORK][WARN] " + message);
    }

    private static void logError(String message) {
        System.out.println("[FRAMEWORK][ERROR] " + message);
    }
}
