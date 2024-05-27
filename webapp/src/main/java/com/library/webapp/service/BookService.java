package com.library.webapp.service;

import com.library.webapp.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBook();
    List<Book> getAllUserFavoriteBook(int id);
}
