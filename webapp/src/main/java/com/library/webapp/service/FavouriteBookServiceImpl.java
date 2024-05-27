package com.library.webapp.service;

import com.library.webapp.exception.DuplicateFavouriteBook;
import com.library.webapp.exception.NoFavouriteBookFound;
import com.library.webapp.model.FavouriteBook;
import com.library.webapp.repository.FavouriteBookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FavouriteBookServiceImpl implements FavouriteBookService{
    private final FavouriteBookRepository favouritebookRepository;

    @Autowired
    public FavouriteBookServiceImpl(FavouriteBookRepository favouritebookRepository) {
        this.favouritebookRepository = favouritebookRepository;
    }

    @Override
    public FavouriteBook saveFavouriteBook(FavouriteBook favouriteBook) {
        if(favouriteBook.getFavouriteBookId() == 0){
            return favouritebookRepository.save(favouriteBook);
        } else{
            if(favouritebookRepository.findSelectedFavouriteBook(favouriteBook.getFavouriteBookId(), favouriteBook.getIsbn().getIsbn()).isEmpty()){
                return favouritebookRepository.save(favouriteBook);
            }else{
                throw new DuplicateFavouriteBook("{\"message\":\"This book is already added to Favourite List!\"}");
            }
        }
    }

    @Override
    public List<FavouriteBook> getAllUserFavoriteBook(int id) {
        return favouritebookRepository.findUserFavoriteBook(id);
    }

    @Override
    public FavouriteBook deleteFavouriteBook(int id, String isbn) {
        List<FavouriteBook> getSelectedFavouriteBook = favouritebookRepository.findSelectedFavouriteBook(id, isbn);
        if(getSelectedFavouriteBook.isEmpty()){
            throw new NoFavouriteBookFound("{\"message\":\"No such book found in favourite list!\"}");
        }else{
            favouritebookRepository.removeBookFromFavourite(id, isbn);
            return getSelectedFavouriteBook.getFirst();
        }
    }
}
