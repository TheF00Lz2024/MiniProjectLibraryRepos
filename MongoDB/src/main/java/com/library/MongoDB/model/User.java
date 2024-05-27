package com.library.login.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    @Id
    private String userId;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "favouriteBookId")
    private FavouriteBook favouriteBookList;
    @OneToMany(mappedBy = "isbn", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> createdBook;
    private String roles;
}
