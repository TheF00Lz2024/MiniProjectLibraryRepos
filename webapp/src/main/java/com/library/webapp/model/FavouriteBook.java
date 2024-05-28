package com.library.webapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "favouritebook")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FavouriteBook {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User username;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "isbn")
    private Book isbn;
}
