package com.library.webapp.repository;

import com.library.webapp.model.Book;
import com.library.webapp.model.FavouriteBook;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface FavouriteBookRepository extends CrudRepository<FavouriteBook, String> {
    // get all user fav book
    @Query(value = "SELECT * FROM favouritebook WHERE favourite_book_id=:favourite_book_id", nativeQuery = true)
    List<FavouriteBook> findUserFavoriteBook(@Param("favourite_book_id")int favouriteBookId);

    //get user selected fav book by fav book id and isbn
    @Query(value = "SELECT * FROM favouritebook WHERE favourite_book_id=:favourite_book_id AND isbn=:isbn", nativeQuery = true)
    List<FavouriteBook> findSelectedFavouriteBook(@Param("favourite_book_id")int favouriteBookId, @Param("isbn")String isbn);

    //remove favbook by fav book id and isbn
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM favouritebook WHERE favourite_book_id=:favourite_book_id AND isbn=:isbn", nativeQuery = true)
    void removeBookFromFavourite(@Param("favouriteBookId")int favouriteBookId, @Param("isbn")String isbn);
}
