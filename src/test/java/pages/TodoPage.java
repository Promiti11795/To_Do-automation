package pages;

import com.microsoft.playwright.Page;
import utils.ConfigReader;
import java.util.*;
import java.util.function.*;

public class TodoPage extends BasePage {

    private String inputBox = ".new-todo";
    private String todoList = ".todo-list li";
    private String toggleCheckbox = ".todo-list li .toggle";
    private String deleteButton = ".todo-list li .destroy";

    public TodoPage(Page page) {
        super(page);
    }

    public TodoPage navigate() {
        // Use URL from config instead of hardcoded
        String url = ConfigReader.getUiBaseUrl();
        page.navigate(url);
        waitForPageLoad();
        return this;
    }

    public TodoPage addTodo(String text) {
        page.locator(inputBox).fill(text);
        page.locator(inputBox).press("Enter");
        return this;
    }

    /**
     * Add multiple todos using forEach + Lambda
     */
    public TodoPage addMultipleTodos(List<String> todos) {
        todos.forEach(todo -> addTodo(todo));
        return this;
    }

    /**
     * Add multiple todos using Method Reference
     */
    public TodoPage addTodosWithMethodReference(List<String> todos) {
        todos.forEach(this::addTodo);
        return this;
    }

    /**
     * Add todos with custom action using Functional Interface
     */
    public TodoPage addTodosWithAction(List<String> todos, Consumer<String> beforeAction) {
        todos.forEach(todo -> {
            beforeAction.accept(todo);  // Execute custom action
            addTodo(todo);
        });
        return this;
    }

    /**
     * Get all todo texts using Stream API
     */
    public List<String> getAllTodoTexts() {
        int count = getTodoCount();
        return java.util.stream.IntStream.range(0, count)
                .mapToObj(this::getTodoText)
                .toList();
    }

    /**
     * Filter todos using Stream + Predicate
     */
    public List<String> filterTodos(Predicate<String> condition) {
        return getAllTodoTexts().stream()
                .filter(condition)
                .toList();
    }

    /**
     * Find todo using Optional
     */
    public Optional<String> findTodo(Predicate<String> condition) {
        return getAllTodoTexts().stream()
                .filter(condition)
                .findFirst();
    }

    public int getTodoCount() {
        return page.locator(todoList).count();
    }

    public String getTodoText(int index) {
        return page.locator(todoList).nth(index).textContent();
    }

    public TodoPage completeTodo(int index) {
        page.locator(toggleCheckbox).nth(index).click();
        return this;
    }

    public TodoPage deleteTodo(int index) {
        page.locator(todoList).nth(index).hover();
        page.locator(deleteButton).nth(index).click();
        return this;
    }
}