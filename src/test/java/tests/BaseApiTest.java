package tests;

import api.ApiClient;
import api.TodoApiService;
import api.UserApiService;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.LoggerUtil;

public class BaseApiTest {

    protected ApiClient apiClient;
    protected TodoApiService todoApiService;
    protected UserApiService userApiService;

    protected static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @BeforeClass
    public void setupApiClient() {
        apiClient = new ApiClient(BASE_URL);
        todoApiService = new TodoApiService(apiClient);
        userApiService = new UserApiService(apiClient);

        LoggerUtil.apiInfo("========================================");
        LoggerUtil.apiInfo("  API Test Setup Complete");
        LoggerUtil.apiInfo("  Base URL: " + BASE_URL);
        LoggerUtil.apiInfo("========================================");
    }

    @BeforeMethod
    public void beforeApiTest() {
        LoggerUtil.clearTestLogs();
    }

    @AfterMethod
    public void afterApiTest(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        if (result.getStatus() == ITestResult.FAILURE) {
            LoggerUtil.apiError("Test FAILED: " + testName);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            LoggerUtil.pass("Test PASSED: " + testName);
        }

        // Attach logs to Allure report
        LoggerUtil.attachLogsToAllure(testName);
    }

    @AfterClass
    public void teardownApiClient() {
        if (apiClient != null) {
            apiClient.close();
        }
        LoggerUtil.apiInfo("API Client closed");

        // Attach full log file to Allure
        LoggerUtil.attachLogFileToAllure("api-tests.log");
    }

    protected void logStep(String message) {
        LoggerUtil.step(message);
    }
}