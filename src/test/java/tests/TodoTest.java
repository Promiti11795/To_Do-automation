package tests;

import org.testng.annotations.*;
import pages.TodoPage;
import static org.testng.Assert.*;

public class TodoTest extends BaseTest {

    private TodoPage todoPage;

    @BeforeMethod
    public void setupTest() {
        todoPage = new TodoPage(page);
        todoPage.navigate();
    }

    @Test
    public void testAddSingleTodo() {
        todoPage.addTodo("Buy groceries");

        assertEquals(todoPage.getTodoCount(), 1);
        assertTrue(todoPage.getTodoText(0).contains("Buy groceries"));
        System.out.println("✓ Test Passed: Add single todo");
    }
}