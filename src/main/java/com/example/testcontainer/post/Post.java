package com.example.testcontainer.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Post {
    public @Id Long id;
    public String title;
    public String body;

    public Post() {}

    public Post(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
