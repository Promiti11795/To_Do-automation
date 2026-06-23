package api;

import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Step;
import models.ApiResponse;
import models.User;

import java.lang.reflect.Type;
import java.util.List;

/**
 * User API Service
 * Provides methods for User operations
 * Uses JSONPlaceholder API: https://jsonplaceholder.typicode.com
 */
public class UserApiService {

    private final ApiClient apiClient;
    private static final String USERS_ENDPOINT = "/users";

    public UserApiService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ==========================================
    // GET Operations
    // ==========================================

    @Step("Get all users")
    public List<User> getAllUsers() {
        ApiResponse<String> response = apiClient.get(USERS_ENDPOINT);

        if (response.isSuccess()) {
            Type listType = new TypeToken<List<User>>(){}.getType();
            return apiClient.getGson().fromJson(response.getRawBody(), listType);
        }
        throw new RuntimeException("Failed to get users. Status: " + response.getStatusCode());
    }

    @Step("Get user by ID: {userId}")
    public User getUserById(Long userId) {
        ApiResponse<String> response = apiClient.get(USERS_ENDPOINT + "/" + userId);

        if (response.isSuccess()) {
            return apiClient.parseResponse(response.getRawBody(), User.class);
        }
        throw new RuntimeException("Failed to get user. Status: " + response.getStatusCode());
    }

    // ==========================================
    // POST Operations
    // ==========================================

    @Step("Create new user: {user.name}")
    public User createUser(User user) {
        ApiResponse<String> response = apiClient.post(USERS_ENDPOINT, user);

        if (response.isCreated() || response.isOk()) {
            return apiClient.parseResponse(response.getRawBody(), User.class);
        }
        throw new RuntimeException("Failed to create user. Status: " + response.getStatusCode());
    }

    // ==========================================
    // PUT Operations
    // ==========================================

    @Step("Update user ID: {userId}")
    public User updateUser(Long userId, User user) {
        ApiResponse<String> response = apiClient.put(USERS_ENDPOINT + "/" + userId, user);

        if (response.isSuccess()) {
            return apiClient.parseResponse(response.getRawBody(), User.class);
        }
        throw new RuntimeException("Failed to update user. Status: " + response.getStatusCode());
    }

    // ==========================================
    // DELETE Operations
    // ==========================================

    @Step("Delete user ID: {userId}")
    public boolean deleteUser(Long userId) {
        ApiResponse<String> response = apiClient.delete(USERS_ENDPOINT + "/" + userId);
        return response.isSuccess();
    }

    // ==========================================
    // Raw API Response (for testing)
    // ==========================================

    public ApiResponse<String> getUserByIdRaw(Long userId) {
        return apiClient.get(USERS_ENDPOINT + "/" + userId);
    }
}