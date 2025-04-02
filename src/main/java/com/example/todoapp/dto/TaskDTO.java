package com.example.todoapp.dto;

import javafx.scene.control.Label;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private String status;
    private List<String> comments;


    public TaskDTO(String title, String description, LocalDateTime dateTime, String status) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
        this.comments = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getComments() {
        return comments;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
