package utils;

import java.util.List;
import java.util.concurrent.*;

/**
 * MULTITHREADING
 *
 * What is it?
 * - Running multiple tasks simultaneously
 * - Faster execution of independent tasks
 *
 * Key Classes Used:
 * - ExecutorService: Manages thread pool
 * - Callable: Task that returns a result
 * - Future: Holds the result of async task
 * - CompletableFuture: Advanced async programming
 */
public class ThreadManager {

    private ExecutorService executor;

    // Constructor - creates thread pool
    public ThreadManager(int numberOfThreads) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
        System.out.println("Thread pool created with " + numberOfThreads + " threads");
    }

    /**
     * Execute multiple todos in parallel using threads
     * Uses: Lambda Expression + Multithreading
     */
    public void executeInParallel(List<String> todos, TodoTask task) {
        System.out.println("\n--- Executing in Parallel ---");

        // Lambda expression inside forEach
        todos.forEach(todo -> {
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("[" + threadName + "] Starting: " + todo);
                task.execute(todo);
                System.out.println("[" + threadName + "] Completed: " + todo);
            });
        });
    }

    /**
     * Execute todos with Callable (returns result)
     * Uses: Callable + Future + Lambda
     */
    public List<Future<String>> executeWithResult(List<String> todos) {
        System.out.println("\n--- Executing with Results ---");

        // Stream API + Lambda + Method Reference
        return todos.stream()
                .map(todo -> executor.submit(() -> {
                    Thread.sleep(100); // Simulate work
                    return "Processed: " + todo;
                }))
                .toList();
    }

    /**
     * Execute using CompletableFuture
     * Uses: CompletableFuture + Lambda + Method Reference
     */
    public CompletableFuture<Void> executeAsync(List<String> todos, TodoTask task) {
        System.out.println("\n--- Executing Async ---");

        // Create array of CompletableFutures
        CompletableFuture<?>[] futures = todos.stream()
                .map(todo -> CompletableFuture.runAsync(() -> {
                    task.logTask(todo);
                    task.execute(todo);
                }, executor))
                .toArray(CompletableFuture[]::new);

        // Wait for all to complete
        return CompletableFuture.allOf(futures);
    }

    // Shutdown thread pool
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        System.out.println("Thread pool shut down");
    }
}