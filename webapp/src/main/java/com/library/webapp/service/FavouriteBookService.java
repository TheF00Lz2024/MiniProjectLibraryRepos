package com.library.webapp.service;

import com.library.webapp.model.Book;
import com.library.webapp.model.FavouriteBook;

import java.util.List;

public interface FavouriteBookService {
    FavouriteBook saveFavouriteBook(FavouriteBook favouriteBook);
    List<FavouriteBook> getAllUserFavoriteBook(int id);
    FavouriteBook deleteFavouriteBook(int id, String isbn);
}
