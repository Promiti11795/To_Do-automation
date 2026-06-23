package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import models.ApiResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * API Client using Playwright's APIRequestContext
 * Provides methods for GET, POST, PUT, PATCH, DELETE
 */
public class ApiClient {

    private final Playwright playwright;
    private final APIRequestContext requestContext;
    private final Gson gson;
    private final String baseUrl;

    /**
     * Constructor - creates API client with base URL
     */
    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.playwright = Playwright.create();
        this.requestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(baseUrl)
                        .setExtraHTTPHeaders(getDefaultHeaders())
        );
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("[API Client] Initialized with base URL: " + baseUrl);
    }

    /**
     * Constructor - with existing Playwright instance
     */
    public ApiClient(Playwright playwright, String baseUrl) {
        this.baseUrl = baseUrl;
        this.playwright = playwright;
        this.requestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(baseUrl)
                        .setExtraHTTPHeaders(getDefaultHeaders())
        );
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("[API Client] Initialized with base URL: " + baseUrl);
    }

    /**
     * Default headers for all requests
     */
    private Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        return headers;
    }

    // ==========================================
    // GET Request
    // ==========================================
    @Step("GET {endpoint}")
    public ApiResponse<String> get(String endpoint) {
        System.out.println("[API] GET " + baseUrl + endpoint);

        APIResponse response = requestContext.get(endpoint);
        ApiResponse<String> apiResponse = buildResponse(response);

        logToAllure("GET", endpoint, null, apiResponse);
        return apiResponse;
    }

    @Step("GET {endpoint} with params")
    public ApiResponse<String> get(String endpoint, Map<String, String> queryParams) {
        System.out.println("[API] GET " + baseUrl + endpoint + " with params: " + queryParams);

        RequestOptions options = RequestOptions.create();
        queryParams.forEach(options::setQueryParam);

        APIResponse response = requestContext.get(endpoint, options);
        ApiResponse<String> apiResponse = buildResponse(response);

        logToAllure("GET", endpoint, queryParams.toString(), apiResponse);
        return apiResponse;
    }

    // ==========================================
    // POST Request
    // ==========================================
    @Step("POST {endpoint}")
    public ApiResponse<String> post(String endpoint, Object body) {
        String jsonBody = gson.toJson(body);
        System.out.println("[API] POST " + baseUrl + endpoint);
        System.out.println("[API] Request Body: " + jsonBody);

        APIResponse response = requestContext.post(endpoint,
                RequestOptions.create().setData(jsonBody));
        ApiResponse<String> apiResponse = buildResponse(response);

        logToAllure("POST", endpoint, jsonBody, apiResponse);
        return apiResponse;
    }

    @Step("POST {endpoint} with JSON string")
    public ApiResponse<String> post(String endpoint, String jsonBody) {
        System.out.println("[API] POST " + baseUrl + endpoint);
        System.out.println("[API] Request Body: " + jsonBody);

        APIResponse response = requestContext.post(endpoint,
                RequestOptions.create().setData(jsonBody));
        ApiResponse<String> apiResponse = buildResponse(response);

        logToAllure("POST", endpoint, jsonBody, apiResponse);
        return apiResponse;
    }

    // ==========================================
    // PUT Request
    // ==========================================
    @Step("PUT {endpoint}")
    public ApiResponse<String> put(String endpoint, Object body) {
        String jsonBody = gson.toJson(body);
        System.out.println("[API] PUT " + baseUrl + endpoint);
        System.out.println("[API] Request Body: " + jsonBody);

        APIResponse response = requestContext.put(endpoint,
                RequestOptions.create().setData(jsonBody));
        ApiResponse<String> apiResponse = buildResponse(response);

        logToAllure("PUT", endpoint, jsonBody, apiResponse);
        return apiResponse;
    }

    // ==========================================
    // PATCH Request
    // ==========================================
    @Step("PATCH {endpoint}")
    public ApiResponse<String> patch(String endpoint, Object body) {
        String jsonBody = gson.toJson(body);
        System.out.println("[API] PATCH " + baseUrl + endpoint);
        System.out.println("[API] Request Body: " + jsonBody);

        APIResponse response = requestContext.patch(endpoint,
                RequestOptions.create().setData(jsonBody));
        ApiResponse<String> apiResponse = buildResponse(response);

        logToAllure("PATCH", endpoint, jsonBody, apiResponse);
        return apiResponse;
    }

    // ==========================================
    // DELETE Request
    // ==========================================
    @Step("DELETE {endpoint}")
    public ApiResponse<String> delete(String endpoint) {
        System.out.println("[API] DELETE " + baseUrl + endpoint);

        APIResponse response = requestContext.delete(endpoint);
        ApiResponse<String> apiResponse = buildResponse(response);

        logToAllure("DELETE", endpoint, null, apiResponse);
        return apiResponse;
    }

    // ==========================================
    // Helper Methods
    // ==========================================

    /**
     * Build ApiResponse from Playwright APIResponse
     */
    private ApiResponse<String> buildResponse(APIResponse response) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(response.status());
        apiResponse.setStatusText(response.statusText());
        apiResponse.setRawBody(response.text());

        // Convert headers
        Map<String, String> headers = new HashMap<>();
        response.headers().forEach((key, value) -> headers.put(key, value));
        apiResponse.setHeaders(headers);

        return apiResponse;
    }

    /**
     * Parse JSON response to specific type
     */
    public <T> T parseResponse(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     * Parse JSON array response to list
     */
    public <T> T parseResponseList(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     * Convert object to JSON string
     */
    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Log API details to Allure report
     */
    private void logToAllure(String method, String endpoint, String requestBody, ApiResponse<String> response) {
        StringBuilder details = new StringBuilder();
        details.append("Method: ").append(method).append("\n");
        details.append("Endpoint: ").append(baseUrl).append(endpoint).append("\n");
        details.append("Status: ").append(response.getStatusCode()).append(" ").append(response.getStatusText()).append("\n");

        if (requestBody != null) {
            details.append("\n--- Request Body ---\n").append(requestBody);
        }

        details.append("\n\n--- Response Body ---\n").append(response.getRawBody());

        Allure.addAttachment(method + " " + endpoint, "text/plain", details.toString(), ".txt");
    }

    /**
     * Close the API client
     */
    public void close() {
        if (requestContext != null) {
            requestContext.dispose();
        }
        System.out.println("[API Client] Closed");
    }

    /**
     * Get Gson instance for custom parsing
     */
    public Gson getGson() {
        return gson;
    }
}