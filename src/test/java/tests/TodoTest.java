package tests;

import io.qameta.allure.*;
import org.testng.annotations.*;
import pages.TodoPage;
import utils.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

import static org.testng.Assert.*;

@Epic("Todo Application")
@Feature("Todo Management")
public class TodoTest extends BaseTest {

    private TodoPage todoPage;
    private TodoDataProvider dataProvider;

    @BeforeMethod
    public void setupTest() {
        todoPage = new TodoPage(page);
        todoPage.navigate();
        dataProvider = new TodoDataProvider();
        logStep("Navigated to Todo application");
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Add Todo")
    @Description("Verify user can add a single todo item")
    public void testAddSingleTodo() {
        logStep("Adding todo: Buy groceries");
        todoPage.addTodo("Buy groceries");

        logStep("Verifying todo count");
        assertEquals(todoPage.getTodoCount(), 1);
        assertTrue(todoPage.getTodoText(0).contains("Buy groceries"));

        takeScreenshot("After Adding Todo");
        System.out.println("✓ Test Passed: Add single todo");
    }

    @Test(priority = 2)
    @Severity(SeverityLevel.NORMAL)
    @Story("Add Multiple Todos")
    @Description("Verify user can add multiple todos using forEach and Lambda")
    public void testAddMultipleTodosWithForEach() {
        List<String> todos = Arrays.asList("Task 1", "Task 2", "Task 3");

        logStep("Adding multiple todos");
        todos.forEach(todo -> {
            System.out.println("Adding: " + todo);
            todoPage.addTodo(todo);
        });

        logStep("Verifying todo count is 3");
        assertEquals(todoPage.getTodoCount(), 3);

        takeScreenshot("After Adding Multiple Todos");
        System.out.println("✓ Test Passed: forEach + Lambda");
    }

    @Test(priority = 3)
    @Severity(SeverityLevel.NORMAL)
    @Story("Add Multiple Todos")
    @Description("Verify adding todos using Method Reference")
    public void testWithMethodReference() {
        List<String> todos = Arrays.asList("Call mom", "Send email", "Buy milk");

        logStep("Adding todos with method reference");
        todoPage.addTodosWithMethodReference(todos);
        todoPage.getAllTodoTexts().forEach(System.out::println);

        logStep("Verifying todo count is 3");
        assertEquals(todoPage.getTodoCount(), 3);

        takeScreenshot("After Method Reference Test");
        System.out.println("✓ Test Passed: Method Reference");
    }

    @Test(priority = 4)
    @Severity(SeverityLevel.NORMAL)
    @Story("Functional Interface")
    @Description("Verify todos using custom Functional Interface")
    public void testWithFunctionalInterface() {
        logStep("Using Functional Interface");
        TodoTask.printHeader();

        TodoTask addTask = (item) -> {
            System.out.println("Executing task: " + item);
            todoPage.addTodo(item);
        };

        List<String> todos = Arrays.asList("Learn Java", "Practice coding");
        todos.forEach(todo -> {
            addTask.logTask(todo);
            addTask.execute(todo);
        });

        logStep("Verifying todo count is 2");
        assertEquals(todoPage.getTodoCount(), 2);

        takeScreenshot("After Functional Interface Test");
        System.out.println("✓ Test Passed: Functional Interface");
    }

    @Test(priority = 5)
    @Severity(SeverityLevel.NORMAL)
    @Story("Stream API")
    @Description("Verify Stream API filter and map operations")
    public void testWithStreamAPI() {
        List<String> todos = Arrays.asList(
                "Buy groceries", "Call doctor", "Buy medicine", "Pay bills", "Buy flowers"
        );

        logStep("Adding 5 todos");
        todoPage.addMultipleTodos(todos);

        logStep("Filtering todos containing 'Buy'");
        List<String> buyTodos = todoPage.filterTodos(todo -> todo.contains("Buy"));
        System.out.println("Todos with 'Buy': " + buyTodos);
        assertEquals(buyTodos.size(), 3);

        logStep("Transforming to uppercase");
        List<String> upperTodos = todoPage.getAllTodoTexts()
                .stream()
                .map(String::toUpperCase)
                .toList();
        System.out.println("Uppercase todos: " + upperTodos);

        takeScreenshot("After Stream API Test");
        System.out.println("✓ Test Passed: Stream API");
    }

    @Test(priority = 6)
    @Severity(SeverityLevel.NORMAL)
    @Story("Optional Class")
    @Description("Verify Optional class for null-safe operations")
    public void testWithOptional() {
        logStep("Adding todos");
        todoPage.addTodo("Important task");
        todoPage.addTodo("Another task");

        logStep("Finding todo with 'Important'");
        // FIXED: Using full path java.util.Optional to avoid ambiguity
        java.util.Optional<String> found = todoPage.findTodo(todo -> todo.contains("Important"));

        assertTrue(found.isPresent());
        System.out.println("Found: " + found.get());

        String result = found.orElse("Not found");
        assertEquals(result, "Important task");

        found.ifPresent(todo -> System.out.println("Processing: " + todo));

        logStep("Testing empty Optional");
        // FIXED: Using full path java.util.Optional to avoid ambiguity
        java.util.Optional<String> notFound = todoPage.findTodo(todo -> todo.contains("XYZ"));
        assertFalse(notFound.isPresent());
        assertEquals(notFound.orElse("Default task"), "Default task");

        takeScreenshot("After Optional Test");
        System.out.println("✓ Test Passed: Optional Class");
    }

    @Test(priority = 7)
    @Severity(SeverityLevel.NORMAL)
    @Story("Stream API")
    @Description("Verify advanced Stream API operations")
    public void testStreamAdvancedOperations() {
        logStep("Adding todos for stream operations");
        dataProvider.addTodos("Apple", "Banana", "Cherry", "Apple", "Date", "Banana");

        logStep("Processing with distinct + sorted");
        List<String> processed = dataProvider.processWithStream();
        System.out.println("Processed: " + processed);

        logStep("Counting todos starting with 'A'");
        long countWithA = dataProvider.countTodos(todo -> todo.startsWith("A"));
        System.out.println("Todos starting with A: " + countWithA);

        boolean hasCherry = dataProvider.anyTodoMatches(todo -> todo.equals("Cherry"));
        assertTrue(hasCherry);

        boolean allShort = dataProvider.allTodosMatch(todo -> todo.length() < 20);
        assertTrue(allShort);

        logStep("Combining todos");
        String combined = dataProvider.combineTodos(" | ");
        System.out.println("Combined: " + combined);

        Map<Integer, List<String>> grouped = dataProvider.groupByLength();
        System.out.println("Grouped by length: " + grouped);

        System.out.println("✓ Test Passed: Stream Advanced Operations");
    }

    @Test(priority = 8)
    @Severity(SeverityLevel.MINOR)
    @Story("Multithreading")
    @Description("Verify multithreading with ExecutorService")
    public void testWithMultithreading() throws Exception {
        logStep("Creating ThreadManager");
        ThreadManager threadManager = new ThreadManager(3);

        List<String> todos = Arrays.asList("Thread Task 1", "Thread Task 2", "Thread Task 3");

        TodoTask task = (item) -> {
            try {
                Thread.sleep(100);
                System.out.println("Processed: " + item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        logStep("Executing in parallel");
        threadManager.executeInParallel(todos, task);
        Thread.sleep(1000);

        logStep("Executing with results");
        List<Future<String>> futures = threadManager.executeWithResult(todos);
        for (Future<String> future : futures) {
            System.out.println("Result: " + future.get());
        }

        logStep("Executing async");
        CompletableFuture<Void> asyncResult = threadManager.executeAsync(todos, task);
        asyncResult.join();

        threadManager.shutdown();
        System.out.println("✓ Test Passed: Multithreading");
    }

    @Test(priority = 9)
    @Severity(SeverityLevel.NORMAL)
    @Story("Functional Interfaces")
    @Description("Verify built-in functional interfaces")
    public void testFunctionalInterfacesBuiltIn() {
        logStep("Testing Consumer");
        Consumer<String> printer = todo -> System.out.println("Todo: " + todo);

        logStep("Testing Predicate");
        Predicate<String> isLong = todo -> todo.length() > 10;

        logStep("Testing Function");
        Function<String, String> addPrefix = todo -> "[URGENT] " + todo;

        logStep("Testing Supplier");
        Supplier<String> defaultTodo = () -> "Default Task";

        String todo = "Buy groceries";

        printer.accept(todo);
        System.out.println("Is long: " + isLong.test(todo));
        System.out.println("With prefix: " + addPrefix.apply(todo));
        System.out.println("Default: " + defaultTodo.get());

        logStep("Adding todos with action");
        todoPage.addTodosWithAction(
                Arrays.asList("Task A", "Task B"),
                item -> System.out.println("Before adding: " + item)
        );

        assertEquals(todoPage.getTodoCount(), 2);

        takeScreenshot("After Functional Interfaces Test");
        System.out.println("✓ Test Passed: Built-in Functional Interfaces");
    }

    @Test(priority = 10)
    @Severity(SeverityLevel.MINOR)
    @Story("Parallel Stream")
    @Description("Verify parallel stream processing")
    public void testParallelStream() {
        List<String> todos = Arrays.asList("Task 1", "Task 2", "Task 3", "Task 4", "Task 5", "Task 6");

        logStep("Sequential Stream");
        System.out.println("\n--- Sequential Stream ---");
        todos.stream()
                .forEach(todo -> System.out.println(Thread.currentThread().getName() + ": " + todo));

        logStep("Parallel Stream");
        System.out.println("\n--- Parallel Stream ---");
        todos.parallelStream()
                .forEach(todo -> System.out.println(Thread.currentThread().getName() + ": " + todo));

        List<String> processed = todos.parallelStream()
                .map(String::toUpperCase)
                .sorted()
                .toList();

        System.out.println("Processed: " + processed);
        System.out.println("✓ Test Passed: Parallel Stream");
    }
}