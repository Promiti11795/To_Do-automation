package models;

import com.google.gson.annotations.SerializedName;

/**
 * Todo Model - represents a Todo item
 * Used for API request/response serialization
 */
public class Todo {

    @SerializedName("id")
    private Long id;

    @SerializedName("title")
    private String title;

    @SerializedName("completed")
    private boolean completed;

    @SerializedName("userId")
    private Long userId;

    // Default constructor
    public Todo() {
    }

    // Constructor with title only
    public Todo(String title) {
        this.title = title;
        this.completed = false;
    }

    // Constructor with all fields
    public Todo(Long id, String title, boolean completed, Long userId) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.userId = userId;
    }

    // Builder pattern for fluent API
    public static TodoBuilder builder() {
        return new TodoBuilder();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                ", userId=" + userId +
                '}';
    }

    // Builder class
    public static class TodoBuilder {
        private Long id;
        private String title;
        private boolean completed;
        private Long userId;

        public TodoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TodoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public TodoBuilder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public TodoBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Todo build() {
            return new Todo(id, title, completed, userId);
        }
    }
}