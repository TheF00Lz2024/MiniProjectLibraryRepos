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
    public Book addBook(Book book) {
        if(bookRepository.findById(book.getIsbn()).isEmpty()){
            return bookRepository.save(book);
        }else{
            throw new DuplicateISBN("{\"message\":\"This ISBN already exist!\"}");
        }
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAllUserFavoriteBook(int id) {
        return bookRepository.findUserFavoriteBook(id);
    }

    @Override
    public Book updateBook(Book book) {
        if(bookRepository.findById(book.getIsbn()).isEmpty()){
            throw new NoBookFound("{\"message\":\"No book with this isbn!\"}");
        }else{
            bookRepository.updateBookDetail(book.getTitle(), book.getIsbn());
            return book;
        }
    }

    @Override
    public Book removeBook(String isbn) {
        Optional<Book> foundBook = bookRepository.findById(isbn);
        if(foundBook.isEmpty()){
            throw new NoBookFound("{\"message\":\"No book with this isbn!\"}");
        }else{
            bookRepository.deleteById(isbn);
            return foundBook.get();
        }
    }
}
