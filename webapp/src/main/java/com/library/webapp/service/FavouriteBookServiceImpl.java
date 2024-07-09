package com.library.webapp.service;

import com.library.webapp.exception.DuplicateFavouriteBook;
import com.library.webapp.exception.NoFavouriteBookFound;
import com.library.webapp.model.Book;
import com.library.webapp.model.FavouriteBook;
import com.library.webapp.repository.FavouriteBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service("favouriteBookServiceImpl")
public class FavouriteBookServiceImpl implements FavouriteBookService{
    private final FavouriteBookRepository favouritebookRepository;

    @Autowired
    public FavouriteBookServiceImpl(FavouriteBookRepository favouritebookRepository) {
        this.favouritebookRepository = favouritebookRepository;
    }

    @Override
    public FavouriteBook saveFavouriteBook(String jwtToken, FavouriteBook favouriteBook) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try{
            ResponseEntity<Book> foundBook = restTemplate.
                    exchange("http://localhost:8083/api/v1/book/"+favouriteBook.getIsbn(), HttpMethod.GET, entity, Book.class);
            if(favouritebookRepository.findSelectedFavouriteBook(favouriteBook.getUsername().getUsername(), Objects.requireNonNull(foundBook.getBody()).getIsbn()).isEmpty()){
                return favouritebookRepository.save(favouriteBook);
            }else{
                throw new DuplicateFavouriteBook("{\"message\":\"This book is already added to Favourite List!\"}");
            }
        }catch (Exception error){
            throw new InternalError(error.getMessage());
        }


    }

    @Override
    public List<FavouriteBook> getAllUserFavoriteBook(String username) {
        return favouritebookRepository.findUserFavoriteBook(username);
    }

    @Override
    public FavouriteBook deleteFavouriteBook(String username, String isbn) {
        List<FavouriteBook> getSelectedFavouriteBook = favouritebookRepository.findSelectedFavouriteBook(username, isbn);
        if(getSelectedFavouriteBook.isEmpty()){
            throw new NoFavouriteBookFound("{\"message\":\"No such book found in favourite list!\"}");
        }else{
            favouritebookRepository.removeBookFromFavourite(username, isbn);
            return getSelectedFavouriteBook.getFirst();
        }
    }
}
