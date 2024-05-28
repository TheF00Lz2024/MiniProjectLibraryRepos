package com.example.mongodb.service;

import com.example.mongodb.model.Book;

public interface BookService {
    Book addBook(Book book);
    Book getBook(String isbn);
    Book updateBook(Book book);
    Book deleteBook(String isbn);
}
