package com.example.mongodb.controller;

import com.example.mongodb.exception.DuplicateISBN;
import com.example.mongodb.exception.NoBookFound;
import com.example.mongodb.model.Book;
import com.example.mongodb.model.BookDTO;
import com.example.mongodb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/testAPI")
    public ResponseEntity<String> testAPI(){
        return new ResponseEntity<>("{\"message\":\"Hello World!\"}", HttpStatus.OK);
    }

    @GetMapping("/book")
    public ResponseEntity<Book> getBook(@RequestParam("isbn")String isbn){
        return new ResponseEntity<>(bookService.getBook(isbn), HttpStatus.FOUND);
    }

    @PostMapping("/book")
    public ResponseEntity<Book> addBook(@RequestBody BookDTO book){
        Book newBook = new Book(book.getIsbn(), book.getTitle(), book.getAuthorName());
        return new ResponseEntity<>(bookService.addBook(newBook), HttpStatus.CREATED);
    }

    @PutMapping("/book")
    public ResponseEntity<Book> updateBook(@RequestBody BookDTO book){
        Book newBook = new Book(book.getIsbn(), book.getTitle(), book.getAuthorName());
        return new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    @DeleteMapping("/book")
    public ResponseEntity<Book> deleteBook(@RequestParam("isbn")String isbn){
        return new ResponseEntity<>(bookService.deleteBook(isbn), HttpStatus.OK);
    }

    @ExceptionHandler(DuplicateISBN.class)
    public ResponseEntity<String> duplicateIsbn(DuplicateISBN message){
        return new ResponseEntity<>(message.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoBookFound.class)
    public ResponseEntity<String> noBookFound(NoBookFound message){
        return new ResponseEntity<>(message.getMessage(), HttpStatus.NOT_FOUND);
    }
}
