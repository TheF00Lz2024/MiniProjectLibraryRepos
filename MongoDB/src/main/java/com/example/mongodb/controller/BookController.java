package com.example.mongodb.controller;

import com.example.mongodb.exception.NoBookFound;
import com.example.mongodb.model.Book;
import com.example.mongodb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/testAPI")
    public ResponseEntity<String> testAPI() {
        return new ResponseEntity<>("{\"message\":\"Hello World!\"}", HttpStatus.OK);
    }

    // API for getting specific book by isbn
    @GetMapping("/book/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable("isbn") String isbn) {
        return new ResponseEntity<>(bookService.getBook(isbn), HttpStatus.FOUND);
    }

    //API for getting all the book from DB
    @GetMapping("/book")
    public ResponseEntity<List<Book>> getAllBook() {
        return new ResponseEntity<>(bookService.getAllBook(), HttpStatus.OK);
    }

    @ExceptionHandler(NoBookFound.class)
    public ResponseEntity<String> noBookFound(NoBookFound message) {
        return new ResponseEntity<>(message.getMessage(), HttpStatus.NOT_FOUND);
    }
}
