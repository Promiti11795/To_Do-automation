package tests;

import com.google.gson.JsonObject;
import io.qameta.allure.*;
import models.Todo;
import org.testng.annotations.*;
import utils.TestDataReader;

import static org.testng.Assert.*;

@Epic("API Testing")
@Feature("Todo API")
public class TodoApiTest extends BaseApiTest {

    @BeforeClass
    public void loadTestData() {
        TestDataReader.loadTestData("testdata/api-testcases.json");
    }

    /**
     * Data Provider for Create Todo tests
     */
    @DataProvider(name = "createTodoData")
    public Object[][] createTodoData() {
        return TestDataReader.getTestDataForProvider("todoTests", "createTodo");
    }

    @Test(dataProvider = "createTodoData")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Create Todo")
    public void testCreateTodoFromTestData(String testId, String testName, JsonObject testCase) {
        // Set description dynamically
        Allure.description(testCase.get("description").getAsString());

        logStep("Executing test: " + testId + " - " + testName);

        // Get input data
        JsonObject input = testCase.getAsJsonObject("input");
        Todo newTodo = Todo.builder()
                .title(input.get("title").getAsString())
                .completed(input.get("completed").getAsBoolean())
                .userId(input.get("userId").getAsLong())
                .build();

        logStep("Creating todo: " + newTodo.getTitle());
        Todo createdTodo = todoApiService.createTodo(newTodo);

        // Validate
        int expectedStatus = testCase.get("expectedStatus").getAsInt();
        assertNotNull(createdTodo, "Created todo should not be null");
        assertNotNull(createdTodo.getId(), "Created todo should have an ID");
        assertEquals(createdTodo.getTitle(), newTodo.getTitle(), "Title should match");

        logStep("✓ Test " + testId + " passed");
    }
}