package com.example.mongodb.service;

import com.example.mongodb.exception.DuplicateISBN;
import com.example.mongodb.exception.NoBookFound;
import com.example.mongodb.model.Book;
import com.example.mongodb.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("bookServiceImpl")
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(Book book) {
        if(bookRepository.findById(book.getIsbn()).isEmpty()){
            return bookRepository.save(book);
        }else{
            throw new DuplicateISBN("{\"message\":\"This ISBN is already in use!\"}");
        }
    }

    @Override
    public Book getBook(String isbn) {
        Optional<Book> foundBook = bookRepository.findById(isbn);
        if(foundBook.isEmpty()){
            throw new NoBookFound("{\"message\":\"There is no book with this ISBN!\"}");
        }else{
            return foundBook.get();
        }
    }

    @Override
    public Book updateBook(Book book) {
        if(bookRepository.findById(book.getIsbn()).isEmpty()){
            throw new NoBookFound("{\"message\":\"There is no book with this ISBN!\"}");
        }else{
            return bookRepository.save(book);
        }
    }

    @Override
    public Book deleteBook(String isbn) {
        Optional<Book> foundBook = bookRepository.findById(isbn);
        if(foundBook.isEmpty()){
            throw new NoBookFound("{\"message\":\"There is no book with this ISBN!\"}");
        }else{
            bookRepository.deleteById(isbn);
            return foundBook.get();
        }
    }
}
