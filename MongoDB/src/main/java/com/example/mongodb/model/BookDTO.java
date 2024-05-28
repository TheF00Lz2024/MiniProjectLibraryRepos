package com.example.mongodb.model;

import lombok.Data;

@Data
public class BookDTO {
    private String isbn;
    private String title;
    private String authorName;
}
