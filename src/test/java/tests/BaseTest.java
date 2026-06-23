package tests;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.LoggerUtil;

import java.io.ByteArrayInputStream;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void setupBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(500)
        );
        LoggerUtil.uiInfo("Browser launched");
    }

    @BeforeMethod
    public void setupPage() {
        context = browser.newContext();
        page = context.newPage();
        LoggerUtil.clearTestLogs();  // Clear logs for new test
        LoggerUtil.uiInfo("New page created");
    }

    @AfterMethod
    public void teardownPage(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        // Take screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            LoggerUtil.uiError("Test FAILED: " + testName);
            takeScreenshotOnFailure(testName);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            LoggerUtil.pass("Test PASSED: " + testName);
        }

        // Attach logs to Allure report
        LoggerUtil.attachLogsToAllure(testName);

        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        LoggerUtil.uiInfo("Page closed");
    }

    @AfterClass
    public void teardownBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
        LoggerUtil.uiInfo("Browser closed");

        // Attach full log file to Allure
        LoggerUtil.attachLogFileToAllure("ui-tests.log");
    }

    @Attachment(value = "Screenshot on Failure: {testName}", type = "image/png")
    public byte[] takeScreenshotOnFailure(String testName) {
        LoggerUtil.uiInfo("Taking screenshot for failed test: " + testName);
        return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }

    public void takeScreenshot(String name) {
        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        LoggerUtil.uiInfo("Screenshot captured: " + name);
    }

    public void logStep(String stepDescription) {
        LoggerUtil.step(stepDescription);
    }
}