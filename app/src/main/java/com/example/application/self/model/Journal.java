package com.example.application.self.model;

import com.google.firebase.Timestamp;

public class Journal {
    private String title;
    private String description;
    private String userId;
    private Timestamp timeAdded;
    private String username;

    public Journal(){
    }

    public Journal(String title, String description, String userId, Timestamp timeAdded, String username) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.timeAdded = timeAdded;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
