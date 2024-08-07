package com.example.mongodb.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Book")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Book {
    @Id
    private String isbn;
    private String title;
    private String authorName;
    private String imgData;
}
