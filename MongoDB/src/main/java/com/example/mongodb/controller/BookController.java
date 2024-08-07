package com.example.mongodb.controller;

import com.example.mongodb.exception.NoBookFound;
import com.example.mongodb.model.Book;
import com.example.mongodb.model.BookDTO;
import com.example.mongodb.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/file")
    public ResponseEntity<Book> uploadImage(@RequestBody BookDTO book){
        Book newBook = new Book(book.getIsbn(), book.getTitle(), book.getAuthorName(), book.getImgData());
        return new ResponseEntity<>(bookService.addBook(newBook), HttpStatus.OK);
    }

    @GetMapping("/testAPI")
    public ResponseEntity<String> testAPI() {
        return new ResponseEntity<>("{\"message\":\"Hello World!\"}", HttpStatus.OK);
    }

    // API for getting user role from JWT claims
    @GetMapping("/book/user/role")
    public ResponseEntity<String> getUserRole(HttpServletRequest httpServletRequest){
        String claim = httpServletRequest.getAttribute("claim").toString();
        String role = claim.split(",")[0].split(":")[1].trim();
        return new ResponseEntity<>("{\"role\":\""+role+"\"}", HttpStatus.OK);
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
