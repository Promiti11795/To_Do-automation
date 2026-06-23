package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Reader Utility
 * Reads properties from config files
 */
public class ConfigReader {

    private static Properties properties;
    private static final String DEFAULT_CONFIG = "config.properties";

    // Private constructor - utility class
    private ConfigReader() {}

    /**
     * Load configuration based on environment
     * Can be set via system property: -Denv=staging
     */
    static {
        loadConfig();
    }

    private static void loadConfig() {
        properties = new Properties();

        // First load default config
        loadPropertiesFile(DEFAULT_CONFIG);

        // Then load environment-specific config (overrides defaults)
        String env = System.getProperty("env", "dev");
        String envConfig = "config-" + env + ".properties";
        loadPropertiesFile(envConfig);

        System.out.println("[ConfigReader] Loaded configuration for environment: " + env);
    }

    private static void loadPropertiesFile(String fileName) {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is != null) {
                properties.load(is);
                System.out.println("[ConfigReader] Loaded: " + fileName);
            } else {
                System.out.println("[ConfigReader] File not found: " + fileName + " (skipping)");
            }
        } catch (IOException e) {
            System.err.println("[ConfigReader] Error loading " + fileName + ": " + e.getMessage());
        }
    }

    /**
     * Reload configuration (useful for tests)
     */
    public static void reload() {
        loadConfig();
    }

    // ==========================================
    // Generic Getters
    // ==========================================

    public static String get(String key) {
        String value = System.getProperty(key);  // Check system property first
        if (value == null) {
            value = properties.getProperty(key);
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long getLong(String key, long defaultValue) {
        String value = get(key);
        try {
            return value != null ? Long.parseLong(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    // ==========================================
    // Convenience Methods - UI Config
    // ==========================================

    public static String getUiBaseUrl() {
        return get("ui.base.url", "https://demo.playwright.dev/todomvc/");
    }

    public static String getBrowserType() {
        return get("browser.type", "chromium");
    }

    public static boolean isHeadless() {
        return getBoolean("browser.headless", false);
    }

    public static int getSlowMo() {
        return getInt("browser.slow.mo", 0);
    }

    public static int getBrowserTimeout() {
        return getInt("browser.timeout", 30000);
    }

    public static boolean isScreenshotOnFailure() {
        return getBoolean("screenshot.on.failure", true);
    }

    public static boolean isFullPageScreenshot() {
        return getBoolean("screenshot.full.page", true);
    }

    // ==========================================
    // Convenience Methods - API Config
    // ==========================================

    public static String getApiBaseUrl() {
        return get("api.base.url", "https://jsonplaceholder.typicode.com");
    }

    public static int getApiTimeout() {
        return getInt("api.timeout", 30000);
    }

    public static int getApiRetryCount() {
        return getInt("api.retry.count", 3);
    }

    public static int getApiRetryDelay() {
        return getInt("api.retry.delay", 1000);
    }

    // ==========================================
    // Convenience Methods - Test Execution
    // ==========================================

    public static boolean isParallelEnabled() {
        return getBoolean("parallel.enabled", false);
    }

    public static int getThreadCount() {
        return getInt("parallel.thread.count", 3);
    }

    // ==========================================
    // Convenience Methods - Reporting
    // ==========================================

    public static String getAllureResultsDir() {
        return get("allure.results.directory", "target/allure-results");
    }

    public static String getLogLevel() {
        return get("log.level", "INFO");
    }

    public static String getLogDirectory() {
        return get("log.directory", "target/logs");
    }

    // ==========================================
    // Convenience Methods - Test Data
    // ==========================================

    public static String getApiTestDataFile() {
        return get("testdata.api.file", "testdata/api-testcases.json");
    }

    public static String getUiTestDataFile() {
        return get("testdata.ui.file", "testdata/ui-testcases.json");
    }

    public static String getEnvironment() {
        return get("environment", "dev");
    }

    // ==========================================
    // Debug - Print all properties
    // ==========================================

    public static void printAllProperties() {
        System.out.println("\n========== CONFIGURATION ==========");
        properties.forEach((key, value) ->
                System.out.println(key + " = " + value)
        );
        System.out.println("====================================\n");
    }
}