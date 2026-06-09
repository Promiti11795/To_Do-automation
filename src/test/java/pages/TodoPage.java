package pages;

import com.microsoft.playwright.Page;

public class TodoPage extends BasePage {

    // Locators
    private String inputBox = ".new-todo";
    private String todoList = ".todo-list li";

    // Constructor
    public TodoPage(Page page) {
        super(page);
    }

    // Navigate to app
    public TodoPage navigate() {
        page.navigate("https://demo.playwright.dev/todomvc/");
        waitForPageLoad();
        return this;
    }

    // Add a todo
    public TodoPage addTodo(String text) {
        page.locator(inputBox).fill(text);
        page.locator(inputBox).press("Enter");
        return this;
    }


    // Get todo count
    public int getTodoCount() {
        return page.locator(todoList).count();
    }

    // Get todo text
    public String getTodoText(int index) {
        return page.locator(todoList).nth(index).textContent();
    }


}