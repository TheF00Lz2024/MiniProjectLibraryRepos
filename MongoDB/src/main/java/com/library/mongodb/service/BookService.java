package com.library.mongodb.service;

import com.library.mongodb.model.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    List<Book> getAllBook();
    List<Book> getAllUserFavoriteBook(int id);
    Book updateBook(Book book);
    Book removeBook(String isbn);
}
