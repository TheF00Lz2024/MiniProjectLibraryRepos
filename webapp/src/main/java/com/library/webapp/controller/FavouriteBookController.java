package com.library.webapp.controller;

import com.library.webapp.exception.DuplicateFavouriteBook;
import com.library.webapp.exception.NoFavouriteBookFound;
import com.library.webapp.model.FavouriteBook;
import com.library.webapp.service.FavouriteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FavouriteBookController {

    private final FavouriteBookService favouriteBookService;

    @Autowired
    public FavouriteBookController(FavouriteBookService favouriteBookService) {
        this.favouriteBookService = favouriteBookService;
    }

    @GetMapping("/testAPI")
    public ResponseEntity<String> testAPI(){
        return new ResponseEntity<>("{\"message\": \"Hello World\"}", HttpStatus.OK);
    }

    //API for getting all user favourite book
    @GetMapping("/favouriteBook")
    public ResponseEntity<List<FavouriteBook>> getUserFavouriteBook(@RequestParam("username")String username){
        return new ResponseEntity<>(favouriteBookService.getAllUserFavoriteBook(username), HttpStatus.OK);
    }

    //API for getting saving user favourite book
    @PostMapping("/favouriteBook")
    public ResponseEntity<FavouriteBook> addUserFavouriteBook(@RequestHeader("Authorization")String jwtToken, @RequestBody FavouriteBook favouriteBook){
        return new ResponseEntity<>(favouriteBookService.saveFavouriteBook(jwtToken, favouriteBook), HttpStatus.CREATED);
    }

    //API for deleting user favourite book
    @DeleteMapping("/favouriteBook")
    public ResponseEntity<FavouriteBook> deleteUserFavouriteBook(@RequestParam("username")String username, @RequestParam("isbn")String isbn){
        return new ResponseEntity<>(favouriteBookService.deleteFavouriteBook(username, isbn), HttpStatus.OK);
    }

    @ExceptionHandler(DuplicateFavouriteBook.class)
    public ResponseEntity<String> duplicateFavouriteBook(DuplicateFavouriteBook message){
        return new ResponseEntity<>(message.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoFavouriteBookFound.class)
    public ResponseEntity<String> noFavouriteBookFound(NoFavouriteBookFound message){
        return new ResponseEntity<>(message.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalError.class)
    public ResponseEntity<String> internalError(InternalError message){
        return new ResponseEntity<>(message.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
