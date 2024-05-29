package com.example.mongodb.service;

import com.example.mongodb.model.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    Book getBook(String isbn);
    List<Book> getAllBook();
    Book updateBook(Book book);
    Book deleteBook(String isbn);
}
