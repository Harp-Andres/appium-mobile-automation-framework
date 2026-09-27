package com.automatizacion.base.hooks;

import com.automatizacion.base.driver.DriverManager;
import com.automatizacion.base.utils.EvidenceCapture;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hooks para captura de evidencias en Cucumber.
 * Ejecutados antes y después de cada escenario.
 */
public class EvidenceHooks {

    private static final Logger log = LoggerFactory.getLogger(EvidenceHooks.class);

    @Before(order = 10, value = "@mobile")
    public void beforeMobileScenario(Scenario scenario) {
        log.info("Iniciando escenario: {}", scenario.getName());

        boolean videoEnabled = Boolean.parseBoolean(System.getProperty("evidence.video.enabled", "false"));
        if (!videoEnabled) {
            return;
        }

        AppiumDriver driver = DriverManager.getDriver();
        if (driver instanceof CanRecordScreen recorder) {
            try {
                recorder.startRecordingScreen();
                log.info("Grabacion de video iniciada");
            } catch (Exception e) {
                log.warn("No se pudo iniciar grabacion: {}", e.getMessage());
            }
        }
    }

    @After(order = 100, value = "@mobile")
    public void afterMobileScenario(Scenario scenario) {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver == null) {
            return;
        }

        boolean screenshotOnPass = Boolean.parseBoolean(System.getProperty("evidence.screenshot.on.pass", "true"));
        boolean takeScreenshot = scenario.isFailed() || screenshotOnPass;
        if (takeScreenshot) {
            String label = scenario.isFailed() ? "FAILED_FINAL_SCREEN" : "PASSED_FINAL_SCREEN";
            EvidenceCapture.attachScreenshot(driver, label + "_" + scenario.getName(), scenario.isFailed());
        }

        if (scenario.isFailed()) {
            log.warn("Escenario fallido: {}", scenario.getName());
        } else {
            log.info("Escenario exitoso: {}", scenario.getName());
        }

        boolean videoEnabled = Boolean.parseBoolean(System.getProperty("evidence.video.enabled", "false"));
        if (videoEnabled && driver instanceof CanRecordScreen recorder) {
            try {
                String base64Video = recorder.stopRecordingScreen();
                String videoName = (scenario.isFailed() ? "FAILED_VIDEO_" : "PASSED_VIDEO_") + scenario.getName();
                EvidenceCapture.attachVideoFromBase64(base64Video, videoName, scenario.isFailed());
            } catch (Exception e) {
                log.warn("No se pudo detener/adjuntar video: {}", e.getMessage());
            }
        }

        EvidenceCapture.attachDriverInfo(driver);
    }
}
