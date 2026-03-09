package com.automatizacion.base.utils;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Utilidades para capturar evidencias (screenshots, logs, video) en Appium.
 */
public class EvidenceCapture {

    private static final String SCREENSHOTS_DIR = "target/screenshots";
    private static final String VIDEOS_DIR = "target/videos";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

    static {
        new File(SCREENSHOTS_DIR).mkdirs();
        new File(VIDEOS_DIR).mkdirs();
    }

    public static void attachScreenshot(AppiumDriver driver, String attachmentName, boolean saveToDisk) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String safeName = sanitizeName(attachmentName);

            if (saveToDisk) {
                String timestamp = LocalDateTime.now().format(FORMATTER);
                String filepath = SCREENSHOTS_DIR + "/" + safeName + "_" + timestamp + ".png";
                try (FileOutputStream fos = new FileOutputStream(filepath)) {
                    fos.write(screenshot);
                }
                System.out.println("[EVIDENCE] Screenshot guardado: " + filepath);
            }

            Allure.getLifecycle().addAttachment(
                attachmentName,
                "image/png",
                "png",
                screenshot
            );
            System.out.println("[EVIDENCE] Screenshot adjuntado a Allure: " + attachmentName);
        } catch (Exception e) {
            System.out.println("[EVIDENCE] Error al capturar screenshot: " + e.getMessage());
        }
    }

    // Metodo legacy para compatibilidad con BasePage y codigo existente.
    public static void captureScreenshot(AppiumDriver driver, String stepName) {
        attachScreenshot(driver, stepName, true);
    }

    public static void captureScreenshotOnFailure(AppiumDriver driver, String scenarioName) {
        attachScreenshot(driver, "FAILED_" + scenarioName, true);
    }

    public static void attachVideoFromBase64(String base64Video, String attachmentName, boolean saveToDisk) {
        if (base64Video == null || base64Video.isBlank()) {
            System.out.println("[EVIDENCE] Video vacio, no se adjunta");
            return;
        }

        try {
            byte[] videoBytes = Base64.getDecoder().decode(base64Video);
            String safeName = sanitizeName(attachmentName);

            if (saveToDisk) {
                String timestamp = LocalDateTime.now().format(FORMATTER);
                String filepath = VIDEOS_DIR + "/" + safeName + "_" + timestamp + ".mp4";
                try (FileOutputStream fos = new FileOutputStream(filepath)) {
                    fos.write(videoBytes);
                }
                System.out.println("[EVIDENCE] Video guardado: " + filepath);
            }

            Allure.getLifecycle().addAttachment(
                attachmentName,
                "video/mp4",
                "mp4",
                videoBytes
            );
            System.out.println("[EVIDENCE] Video adjuntado a Allure: " + attachmentName);
        } catch (Exception e) {
            System.out.println("[EVIDENCE] Error al adjuntar video: " + e.getMessage());
        }
    }

    public static void attachLog(String title, String content) {
        try {
            Allure.addAttachment(title, "text/plain", content, "txt");
            System.out.println("[EVIDENCE] Log adjuntado: " + title);
        } catch (Exception e) {
            System.out.println("[EVIDENCE] Error al adjuntar log: " + e.getMessage());
        }
    }

    public static void attachDriverInfo(AppiumDriver driver) {
        try {
            String driverInfo = "Session ID: " + driver.getSessionId() + "\n"
                + "Platform: " + driver.getCapabilities().getCapability("platformName") + "\n"
                + "Device: " + driver.getCapabilities().getCapability("deviceName") + "\n"
                + "App: " + driver.getCapabilities().getCapability("app");

            attachLog("Driver Info", driverInfo);
        } catch (Exception e) {
            System.out.println("[EVIDENCE] Error al adjuntar driver info: " + e.getMessage());
        }
    }

    private static String sanitizeName(String raw) {
        return raw == null ? "evidence" : raw.replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}
