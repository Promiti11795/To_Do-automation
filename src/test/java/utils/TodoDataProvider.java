package utils;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * STREAM API, OPTIONAL, LAMBDA, METHOD REFERENCES
 *
 * Stream API: Process collections in functional style
 * Optional: Handle null values safely
 * Lambda: Short anonymous functions
 * Method Reference: Shorthand for lambdas
 */
public class TodoDataProvider {

    private List<String> todos;

    // Constructor
    public TodoDataProvider() {
        this.todos = new ArrayList<>();
    }

    // Add todos
    public void addTodos(String... items) {
        Collections.addAll(todos, items);
    }

    /**
     * STREAM API - Filter todos
     * Uses: stream(), filter(), collect()
     */
    public List<String> filterTodos(Predicate<String> condition) {
        return todos.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    /**
     * STREAM API - Transform todos
     * Uses: stream(), map(), collect()
     */
    public List<String> transformTodos(Function<String, String> transformer) {
        return todos.stream()
                .map(transformer)
                .collect(Collectors.toList());
    }

    /**
     * STREAM API - Multiple operations
     * Uses: filter(), map(), sorted(), distinct()
     */
    public List<String> processWithStream() {
        return todos.stream()
                .filter(todo -> todo.length() > 3)           // Filter: length > 3
                .map(String::toUpperCase)                     // Method Reference: to uppercase
                .sorted()                                     // Sort alphabetically
                .distinct()                                   // Remove duplicates
                .collect(Collectors.toList());
    }

    /**
     * forEach - Iterate with Lambda
     * Uses: forEach() + Lambda Expression
     */
    public void printAllTodos() {
        System.out.println("\n--- All Todos (forEach + Lambda) ---");

        // Lambda expression
        todos.forEach(todo -> System.out.println("• " + todo));
    }

    /**
     * forEach - With Method Reference
     * Uses: forEach() + Method Reference
     */
    public void printTodosWithMethodReference() {
        System.out.println("\n--- All Todos (Method Reference) ---");

        // Method Reference (shorter than lambda)
        todos.forEach(System.out::println);
    }

    /**
     * OPTIONAL - Safe null handling
     * Uses: Optional.ofNullable(), orElse(), ifPresent()
     */
    public Optional<String> findTodoByIndex(int index) {
        if (index >= 0 && index < todos.size()) {
            return Optional.ofNullable(todos.get(index));
        }
        return Optional.empty();
    }

    /**
     * OPTIONAL - With default value
     */
    public String getTodoOrDefault(int index, String defaultValue) {
        return findTodoByIndex(index).orElse(defaultValue);
    }

    /**
     * OPTIONAL - Execute if present
     */
    public void processTodoIfExists(int index, Consumer<String> action) {
        findTodoByIndex(index).ifPresent(action);
    }

    /**
     * OPTIONAL - With orElseThrow
     */
    public String getTodoOrThrow(int index) {
        return findTodoByIndex(index)
                .orElseThrow(() -> new NoSuchElementException("Todo not found at index: " + index));
    }

    /**
     * STREAM - Count todos matching condition
     */
    public long countTodos(Predicate<String> condition) {
        return todos.stream()
                .filter(condition)
                .count();
    }

    /**
     * STREAM - Check if any todo matches
     */
    public boolean anyTodoMatches(Predicate<String> condition) {
        return todos.stream().anyMatch(condition);
    }

    /**
     * STREAM - Check if all todos match
     */
    public boolean allTodosMatch(Predicate<String> condition) {
        return todos.stream().allMatch(condition);
    }

    /**
     * STREAM - Find first matching todo
     */
    public Optional<String> findFirst(Predicate<String> condition) {
        return todos.stream()
                .filter(condition)
                .findFirst();
    }

    /**
     * STREAM - Reduce to single value
     */
    public String combineTodos(String delimiter) {
        return todos.stream()
                .reduce((a, b) -> a + delimiter + b)
                .orElse("");
    }

    /**
     * STREAM - Group by length
     */
    public Map<Integer, List<String>> groupByLength() {
        return todos.stream()
                .collect(Collectors.groupingBy(String::length));
    }

    // Get all todos
    public List<String> getAllTodos() {
        return new ArrayList<>(todos);
    }

    // Get count
    public int getCount() {
        return todos.size();
    }
}