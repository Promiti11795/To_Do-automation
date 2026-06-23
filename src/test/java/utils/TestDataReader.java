package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to read test data from JSON files
 */
public class TestDataReader {

    private static final Gson gson = new Gson();
    private static JsonObject testData;

    /**
     * Load test data from JSON file
     */
    public static void loadTestData(String fileName) {
        try (InputStream is = TestDataReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new RuntimeException("Test data file not found: " + fileName);
            }
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            testData = gson.fromJson(reader, JsonObject.class);
            System.out.println("[TestDataReader] Loaded test data from: " + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data: " + fileName, e);
        }
    }

    /**
     * Get test cases for a specific category
     * Example: getTestCases("todoTests", "createTodo")
     */
    public static JsonArray getTestCases(String category, String testType) {
        if (testData == null) {
            loadTestData("testdata/api-testcases.json");
        }
        return testData.getAsJsonObject(category).getAsJsonArray(testType);
    }

    /**
     * Get single test case by ID
     */
    public static JsonObject getTestCaseById(String testId) {
        if (testData == null) {
            loadTestData("testdata/api-testcases.json");
        }

        // Search through all categories
        for (String category : testData.keySet()) {
            JsonObject categoryObj = testData.getAsJsonObject(category);
            for (String testType : categoryObj.keySet()) {
                JsonArray tests = categoryObj.getAsJsonArray(testType);
                for (JsonElement test : tests) {
                    JsonObject testCase = test.getAsJsonObject();
                    if (testCase.get("testId").getAsString().equals(testId)) {
                        return testCase;
                    }
                }
            }
        }
        throw new RuntimeException("Test case not found: " + testId);
    }

    /**
     * Get test data as specific class
     */
    public static <T> T getTestDataAs(String testId, String field, Class<T> clazz) {
        JsonObject testCase = getTestCaseById(testId);
        JsonElement element = testCase.get(field);
        return gson.fromJson(element, clazz);
    }

    /**
     * Convert JsonObject to specified class
     */
    public static <T> T convertTo(JsonObject json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     * Get all test IDs for DataProvider
     */
    public static Object[][] getTestDataForProvider(String category, String testType) {
        JsonArray tests = getTestCases(category, testType);
        List<Object[]> data = new ArrayList<>();

        for (JsonElement test : tests) {
            JsonObject testCase = test.getAsJsonObject();
            data.add(new Object[]{
                    testCase.get("testId").getAsString(),
                    testCase.get("testName").getAsString(),
                    testCase
            });
        }

        return data.toArray(new Object[0][]);
    }
}