package utils;

/**
 * FUNCTIONAL INTERFACE
 *
 * What is it?
 * - An interface with exactly ONE abstract method
 * - Can be used with Lambda expressions
 * - @FunctionalInterface annotation ensures only one method
 *
 * Why use it?
 * - Enables Lambda expressions
 * - Cleaner, shorter code
 * - Can pass behavior as a parameter
 */
@FunctionalInterface
public interface TodoTask {

    // Single abstract method
    void execute(String todoItem);

    // Default method (allowed in functional interface)
    default void logTask(String todoItem) {
        System.out.println("[LOG] Processing: " + todoItem);
    }

    // Static method (allowed in functional interface)
    static void printHeader() {
        System.out.println("========================================");
        System.out.println("       TODO TASK EXECUTION");
        System.out.println("========================================");
    }
}