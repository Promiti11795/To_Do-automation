package tests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    @BeforeClass
    public void setupBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(500)
        );
        System.out.println("Browser launched");
    }

    @BeforeMethod
    public void setupPage() {
        page = browser.newPage();
        System.out.println("New page created");
    }

    @AfterMethod
    public void teardownPage() {
        if (page != null) {
            page.close();
        }
        System.out.println("Page closed");
    }

    @AfterClass
    public void teardownBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
        System.out.println("Browser closed");
    }
}