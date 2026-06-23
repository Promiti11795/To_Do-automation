package tests;

import io.qameta.allure.*;
import models.User;
import org.testng.annotations.*;

import java.util.List;

import static org.testng.Assert.*;

/**
 * API Tests for User endpoints
 * Uses JSONPlaceholder API: https://jsonplaceholder.typicode.com
 */
@Epic("API Testing")
@Feature("User API")
public class UserApiTest extends BaseApiTest {

    // ==========================================
    // GET Tests
    // ==========================================

    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Get Users")
    @Description("Verify GET /users returns all users")
    public void testGetAllUsers() {
        logStep("Getting all users from API");

        List<User> users = userApiService.getAllUsers();

        logStep("Verifying users list");
        assertNotNull(users, "Users list should not be null");
        assertFalse(users.isEmpty(), "Users list should not be empty");
        assertEquals(users.size(), 10, "Should have 10 users");

        logStep("Found " + users.size() + " users");
        System.out.println("✓ Test Passed: Get all users");
    }

    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Get Users")
    @Description("Verify GET /users/1 returns specific user")
    public void testGetUserById() {
        logStep("Getting user with ID = 1");

        User user = userApiService.getUserById(1L);

        logStep("Verifying user details");
        assertNotNull(user, "User should not be null");
        assertEquals(user.getId(), Long.valueOf(1), "User ID should be 1");
        assertEquals(user.getName(), "Leanne Graham", "User name should match");
        assertEquals(user.getUsername(), "Bret", "Username should match");
        assertNotNull(user.getEmail(), "Email should not be null");

        logStep("User found: " + user.getName());
        System.out.println("✓ Test Passed: Get user by ID");
    }

    // ==========================================
    // POST Tests
    // ==========================================

    @Test(priority = 3)
    @Severity(SeverityLevel.NORMAL)
    @Story("Create User")
    @Description("Verify POST /users creates a new user")
    public void testCreateUser() {
        logStep("Creating new user");

        User newUser = new User("John Doe", "johndoe", "john@example.com");

        logStep("Sending POST request");
        User createdUser = userApiService.createUser(newUser);

        logStep("Verifying created user");
        assertNotNull(createdUser, "Created user should not be null");
        assertNotNull(createdUser.getId(), "User should have an ID");
        assertEquals(createdUser.getName(), "John Doe", "Name should match");
        assertEquals(createdUser.getUsername(), "johndoe", "Username should match");
        assertEquals(createdUser.getEmail(), "john@example.com", "Email should match");

        logStep("Created user with ID: " + createdUser.getId());
        System.out.println("✓ Test Passed: Create user");
    }

    // ==========================================
    // PUT Tests
    // ==========================================

    @Test(priority = 4)
    @Severity(SeverityLevel.NORMAL)
    @Story("Update User")
    @Description("Verify PUT /users/1 updates user")
    public void testUpdateUser() {
        logStep("Updating user with ID = 1");

        User updatedUser = new User("Jane Doe Updated", "janedoe", "jane@example.com");
        updatedUser.setId(1L);

        logStep("Sending PUT request");
        User result = userApiService.updateUser(1L, updatedUser);

        logStep("Verifying updated user");
        assertNotNull(result, "Updated user should not be null");
        assertEquals(result.getName(), "Jane Doe Updated", "Name should be updated");

        System.out.println("✓ Test Passed: Update user");
    }

    // ==========================================
    // DELETE Tests
    // ==========================================

    @Test(priority = 5)
    @Severity(SeverityLevel.NORMAL)
    @Story("Delete User")
    @Description("Verify DELETE /users/1 deletes user")
    public void testDeleteUser() {
        logStep("Deleting user with ID = 1");

        boolean isDeleted = userApiService.deleteUser(1L);

        logStep("Verifying deletion");
        assertTrue(isDeleted, "User should be deleted successfully");

        System.out.println("✓ Test Passed: Delete user");
    }

    // ==========================================
    // Stream API Tests
    // ==========================================

    @Test(priority = 6)
    @Severity(SeverityLevel.MINOR)
    @Story("Stream API")
    @Description("Use Stream API to process users")
    public void testStreamApiWithUsers() {
        logStep("Processing users with Stream API");

        List<User> users = userApiService.getAllUsers();

        // Get all usernames
        logStep("Extracting all usernames");
        List<String> usernames = users.stream()
                .map(User::getUsername)
                .toList();
        System.out.println("Usernames: " + usernames);

        // Find user with specific email domain
        logStep("Finding users with .biz email");
        List<User> bizUsers = users.stream()
                .filter(user -> user.getEmail().endsWith(".biz"))
                .toList();
        System.out.println("Users with .biz email: " + bizUsers.size());

        // Get user names sorted
        logStep("Sorting user names");
        List<String> sortedNames = users.stream()
                .map(User::getName)
                .sorted()
                .toList();
        System.out.println("Sorted names: " + sortedNames);

        // Find any user with website
        logStep("Checking if any user has website");
        boolean hasWebsite = users.stream()
                .anyMatch(user -> user.getWebsite() != null && !user.getWebsite().isEmpty());
        assertTrue(hasWebsite, "At least one user should have website");

        System.out.println("✓ Test Passed: Stream API with users");
    }
}