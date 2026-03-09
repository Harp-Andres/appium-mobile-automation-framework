package com.automatizacion.base.pages;

import com.automatizacion.base.driver.DriverManager;
import com.automatizacion.base.utils.EvidenceCapture;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Clase base para Page Objects con soporte multiplataforma Appium.
 * Centraliza esperas explícitas e integración con captura de evidencias.
 */
public abstract class BasePage {

    protected AppiumDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected String textOf(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    protected boolean isVisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    /**
     * Capturar screenshot en momento crítico.
     * CENTRALIZADO en BasePage para acceso desde cualquier Page/Action.
     */
    public void captureEvidence(String stepName) {
        EvidenceCapture.captureScreenshot(driver, stepName);
    }
}
