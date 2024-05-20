package com.library.login.model;

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
    private int id;
    @OneToMany(mappedBy = "isbn", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> favouriteBookSet;
}
