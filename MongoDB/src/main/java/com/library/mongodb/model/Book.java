package com.library.mongodb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Book {
    @Id
    private String isbn;
    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private User authorName;
}
