package com.library.webapp.service;

import com.library.webapp.exception.DuplicateFavouriteBook;
import com.library.webapp.exception.NoFavouriteBookFound;
import com.library.webapp.model.FavouriteBook;
import com.library.webapp.repository.FavouriteBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("favouriteBookServiceImpl")
public class FavouriteBookServiceImpl implements FavouriteBookService{
    private final FavouriteBookRepository favouritebookRepository;

    @Autowired
    public FavouriteBookServiceImpl(FavouriteBookRepository favouritebookRepository) {
        this.favouritebookRepository = favouritebookRepository;
    }

    @Override
    public FavouriteBook saveFavouriteBook(FavouriteBook favouriteBook) {
        if(favouritebookRepository.findSelectedFavouriteBook(favouriteBook.getUsername().getUsername(), favouriteBook.getIsbn().getIsbn()).isEmpty()){
            return favouritebookRepository.save(favouriteBook);
        }else{
            throw new DuplicateFavouriteBook("{\"message\":\"This book is already added to Favourite List!\"}");
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
