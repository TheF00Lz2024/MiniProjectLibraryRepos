package com.library.mongodb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "favouritebook")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FavouriteBook {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "favourite_book_id")
    private int favouriteBookId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "isbn")
    private Book isbn;
}
