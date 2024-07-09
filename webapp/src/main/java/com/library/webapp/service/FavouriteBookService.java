package com.library.webapp.service;

import com.library.webapp.model.FavouriteBook;

import java.util.List;

public interface FavouriteBookService {
    FavouriteBook saveFavouriteBook(String jwtToken, FavouriteBook favouriteBook);

    List<FavouriteBook> getAllUserFavoriteBook(String username);

    FavouriteBook deleteFavouriteBook(String username, String isbn);
}
