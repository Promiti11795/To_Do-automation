package api;

import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Step;
import models.ApiResponse;
import models.Todo;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Todo API Service
 * Provides methods for Todo CRUD operations
 * Uses JSONPlaceholder API: https://jsonplaceholder.typicode.com
 */
public class TodoApiService {

    private final ApiClient apiClient;
    private static final String TODOS_ENDPOINT = "/todos";

    public TodoApiService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ==========================================
    // GET Operations
    // ==========================================

    @Step("Get all todos")
    public List<Todo> getAllTodos() {
        ApiResponse<String> response = apiClient.get(TODOS_ENDPOINT);

        if (response.isSuccess()) {
            Type listType = new TypeToken<List<Todo>>(){}.getType();
            return apiClient.getGson().fromJson(response.getRawBody(), listType);
        }
        throw new RuntimeException("Failed to get todos. Status: " + response.getStatusCode());
    }

    @Step("Get todo by ID: {todoId}")
    public Todo getTodoById(Long todoId) {
        ApiResponse<String> response = apiClient.get(TODOS_ENDPOINT + "/" + todoId);

        if (response.isSuccess()) {
            return apiClient.parseResponse(response.getRawBody(), Todo.class);
        }
        throw new RuntimeException("Failed to get todo. Status: " + response.getStatusCode());
    }

    @Step("Get todos by user ID: {userId}")
    public List<Todo> getTodosByUserId(Long userId) {
        ApiResponse<String> response = apiClient.get(TODOS_ENDPOINT + "?userId=" + userId);

        if (response.isSuccess()) {
            Type listType = new TypeToken<List<Todo>>(){}.getType();
            return apiClient.getGson().fromJson(response.getRawBody(), listType);
        }
        throw new RuntimeException("Failed to get todos for user. Status: " + response.getStatusCode());
    }

    // ==========================================
    // POST Operations
    // ==========================================

    @Step("Create new todo: {todo.title}")
    public Todo createTodo(Todo todo) {
        ApiResponse<String> response = apiClient.post(TODOS_ENDPOINT, todo);

        if (response.isCreated() || response.isOk()) {
            return apiClient.parseResponse(response.getRawBody(), Todo.class);
        }
        throw new RuntimeException("Failed to create todo. Status: " + response.getStatusCode());
    }

    @Step("Create todo with title: {title}")
    public Todo createTodo(String title, boolean completed, Long userId) {
        Todo todo = Todo.builder()
                .title(title)
                .completed(completed)
                .userId(userId)
                .build();
        return createTodo(todo);
    }

    // ==========================================
    // PUT Operations
    // ==========================================

    @Step("Update todo ID: {todoId}")
    public Todo updateTodo(Long todoId, Todo todo) {
        ApiResponse<String> response = apiClient.put(TODOS_ENDPOINT + "/" + todoId, todo);

        if (response.isSuccess()) {
            return apiClient.parseResponse(response.getRawBody(), Todo.class);
        }
        throw new RuntimeException("Failed to update todo. Status: " + response.getStatusCode());
    }

    // ==========================================
    // PATCH Operations
    // ==========================================

    @Step("Patch todo ID: {todoId}")
    public Todo patchTodo(Long todoId, Object partialUpdate) {
        ApiResponse<String> response = apiClient.patch(TODOS_ENDPOINT + "/" + todoId, partialUpdate);

        if (response.isSuccess()) {
            return apiClient.parseResponse(response.getRawBody(), Todo.class);
        }
        throw new RuntimeException("Failed to patch todo. Status: " + response.getStatusCode());
    }

    @Step("Mark todo as completed: {todoId}")
    public Todo markAsCompleted(Long todoId) {
        return patchTodo(todoId, java.util.Map.of("completed", true));
    }

    @Step("Mark todo as incomplete: {todoId}")
    public Todo markAsIncomplete(Long todoId) {
        return patchTodo(todoId, java.util.Map.of("completed", false));
    }

    // ==========================================
    // DELETE Operations
    // ==========================================

    @Step("Delete todo ID: {todoId}")
    public boolean deleteTodo(Long todoId) {
        ApiResponse<String> response = apiClient.delete(TODOS_ENDPOINT + "/" + todoId);
        return response.isSuccess();
    }

    // ==========================================
    // Raw API Response (for testing status codes)
    // ==========================================

    public ApiResponse<String> getTodoByIdRaw(Long todoId) {
        return apiClient.get(TODOS_ENDPOINT + "/" + todoId);
    }

    public ApiResponse<String> createTodoRaw(Todo todo) {
        return apiClient.post(TODOS_ENDPOINT, todo);
    }

    public ApiResponse<String> deleteTodoRaw(Long todoId) {
        return apiClient.delete(TODOS_ENDPOINT + "/" + todoId);
    }
}