package com.library.webapp.model;

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
    @JoinColumn(name = "favouriteBookSet")
    private FavouriteBook favouriteListId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User authorName;
}
