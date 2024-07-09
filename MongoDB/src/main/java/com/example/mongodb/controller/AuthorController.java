package com.example.mongodb.controller;

import com.example.mongodb.exception.DuplicateISBN;
import com.example.mongodb.model.Book;
import com.example.mongodb.model.BookDTO;
import com.example.mongodb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {

    private final BookService bookService;

    @Autowired
    public AuthorController(BookService bookService) {
        this.bookService = bookService;
    }

    //API for adding new book to DB
    @PostMapping("/author/book")
    public ResponseEntity<Book> addBook(@RequestBody BookDTO book) {
        Book newBook = new Book(book.getIsbn(), book.getTitle(), book.getAuthorName());
        return new ResponseEntity<>(bookService.addBook(newBook), HttpStatus.CREATED);
    }

    //API for updating book to DB
    @PutMapping("/author/book")
    public ResponseEntity<Book> updateBook(@RequestBody BookDTO book) {
        Book newBook = new Book(book.getIsbn(), book.getTitle(), book.getAuthorName());
        return new ResponseEntity<>(bookService.updateBook(newBook), HttpStatus.OK);
    }

    //API for deleting book from DB
    @DeleteMapping("/author/book")
    public ResponseEntity<Book> deleteBook(@RequestParam("isbn") String isbn) {
        return new ResponseEntity<>(bookService.deleteBook(isbn), HttpStatus.OK);
    }

    @ExceptionHandler(DuplicateISBN.class)
    public ResponseEntity<String> duplicateIsbn(DuplicateISBN message) {
        return new ResponseEntity<>(message.getMessage(), HttpStatus.FORBIDDEN);
    }
}
