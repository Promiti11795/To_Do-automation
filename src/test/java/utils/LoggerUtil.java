package utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Centralized logging utility
 * Logs to Console + File + Allure Report
 */
public class LoggerUtil {

    private static final Logger uiLogger = LogManager.getLogger("ui");
    private static final Logger apiLogger = LogManager.getLogger("api");

    // Store logs for attaching to Allure
    private static final StringBuilder testLogs = new StringBuilder();

    // ==========================================
    // UI Test Logging
    // ==========================================

    public static void uiInfo(String message) {
        uiLogger.info(message);
        appendToTestLog("INFO", message);
        Allure.step(message);
    }

    public static void uiDebug(String message) {
        uiLogger.debug(message);
        appendToTestLog("DEBUG", message);
    }

    public static void uiError(String message) {
        uiLogger.error(message);
        appendToTestLog("ERROR", message);
        Allure.step("❌ ERROR: " + message);
    }

    public static void uiError(String message, Throwable throwable) {
        uiLogger.error(message, throwable);
        appendToTestLog("ERROR", message + " - " + throwable.getMessage());
        Allure.step("❌ ERROR: " + message);
    }

    // ==========================================
    // API Test Logging
    // ==========================================

    public static void apiInfo(String message) {
        apiLogger.info(message);
        appendToTestLog("INFO", message);
        Allure.step(message);
    }

    public static void apiDebug(String message) {
        apiLogger.debug(message);
        appendToTestLog("DEBUG", message);
    }

    public static void apiError(String message) {
        apiLogger.error(message);
        appendToTestLog("ERROR", message);
        Allure.step("❌ ERROR: " + message);
    }

    public static void apiRequest(String method, String endpoint, String body) {
        String message = String.format("REQUEST: %s %s", method, endpoint);
        apiLogger.info(message);
        if (body != null && !body.isEmpty()) {
            apiLogger.debug("Request Body: " + body);
        }
        appendToTestLog("REQUEST", message);
    }

    public static void apiResponse(int statusCode, String body) {
        String message = String.format("RESPONSE: Status %d", statusCode);
        apiLogger.info(message);
        apiLogger.debug("Response Body: " + body);
        appendToTestLog("RESPONSE", message);
    }

    // ==========================================
    // Generic Logging
    // ==========================================

    public static void info(String message) {
        LogManager.getLogger().info(message);
        appendToTestLog("INFO", message);
    }

    public static void step(String stepDescription) {
        LogManager.getLogger().info("[STEP] " + stepDescription);
        appendToTestLog("STEP", stepDescription);
        Allure.step(stepDescription);
    }

    public static void pass(String message) {
        LogManager.getLogger().info("✓ PASS: " + message);
        appendToTestLog("PASS", message);
        Allure.step("✓ " + message);
    }

    public static void fail(String message) {
        LogManager.getLogger().error("✗ FAIL: " + message);
        appendToTestLog("FAIL", message);
        Allure.step("✗ " + message);
    }

    // ==========================================
    // Test Log Management
    // ==========================================

    private static void appendToTestLog(String level, String message) {
        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        testLogs.append(String.format("[%s] %s: %s%n", timestamp, level, message));
    }

    /**
     * Clear test logs (call at start of each test)
     */
    public static void clearTestLogs() {
        testLogs.setLength(0);
    }

    /**
     * Get current test logs
     */
    public static String getTestLogs() {
        return testLogs.toString();
    }

    /**
     * Attach test logs to Allure report
     */
    public static void attachLogsToAllure(String testName) {
        if (testLogs.length() > 0) {
            String logs = testLogs.toString();
            Allure.addAttachment(
                    "Test Logs: " + testName,
                    "text/plain",
                    new ByteArrayInputStream(logs.getBytes(StandardCharsets.UTF_8)),
                    ".log"
            );
        }
    }

    /**
     * Attach log file to Allure report
     */
    public static void attachLogFileToAllure(String logFileName) {
        try {
            Path logPath = Paths.get("target/logs/" + logFileName);
            if (Files.exists(logPath)) {
                byte[] logContent = Files.readAllBytes(logPath);
                Allure.addAttachment(
                        "Log File: " + logFileName,
                        "text/plain",
                        new ByteArrayInputStream(logContent),
                        ".log"
                );
            }
        } catch (IOException e) {
            apiLogger.error("Failed to attach log file: " + logFileName, e);
        }
    }
}