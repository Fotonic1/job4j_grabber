package ru.job4j.grabber;

import java.time.LocalDateTime;

public class Post {
    int id;
    LocalDateTime creationDate;
    LocalDateTime updatingDate;
    String href;
    String name;
    String description;

    public void setId(int id) {
        this.id = id;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdatingDate(LocalDateTime updatingDate) {
        this.updatingDate = updatingDate;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getUpdatingDate() {
        return updatingDate;
    }

    public String getHref() {
        return href;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
