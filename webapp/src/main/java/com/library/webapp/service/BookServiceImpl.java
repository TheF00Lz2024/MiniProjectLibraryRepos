package com.library.webapp.service;

import com.library.webapp.exception.DuplicateISBN;
import com.library.webapp.exception.NoBookFound;
import com.library.webapp.model.Book;
import com.library.webapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAllUserFavoriteBook(int id) {
        return bookRepository.findUserFavoriteBook(id);
    }
}
