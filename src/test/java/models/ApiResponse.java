package models;

import java.util.Map;

/**
 * Generic API Response wrapper
 * Holds status code, headers, and body
 */
public class ApiResponse<T> {

    private int statusCode;
    private String statusText;
    private Map<String, String> headers;
    private String rawBody;
    private T body;

    public ApiResponse() {
    }

    public ApiResponse(int statusCode, String statusText, Map<String, String> headers, String rawBody) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.headers = headers;
        this.rawBody = rawBody;
    }

    // Check if response is successful (2xx)
    public boolean isSuccess() {
        return statusCode >= 200 && statusCode < 300;
    }

    // Check specific status codes
    public boolean isOk() {
        return statusCode == 200;
    }

    public boolean isCreated() {
        return statusCode == 201;
    }

    public boolean isNotFound() {
        return statusCode == 404;
    }

    public boolean isBadRequest() {
        return statusCode == 400;
    }

    public boolean isUnauthorized() {
        return statusCode == 401;
    }

    public boolean isServerError() {
        return statusCode >= 500;
    }

    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getRawBody() {
        return rawBody;
    }

    public void setRawBody(String rawBody) {
        this.rawBody = rawBody;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", statusText='" + statusText + '\'' +
                ", body=" + rawBody +
                '}';
    }
}