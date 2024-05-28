package com.library.webapp.repository;

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
    @Query(value = "SELECT * FROM favouritebook WHERE username=:username", nativeQuery = true)
    List<FavouriteBook> findUserFavoriteBook(@Param("username")String username);

    //get user selected fav book by fav book id and isbn
    @Query(value = "SELECT * FROM favouritebook WHERE username=:username AND isbn=:isbn", nativeQuery = true)
    List<FavouriteBook> findSelectedFavouriteBook(@Param("username")String username, @Param("isbn")String isbn);

    //remove favbook by fav book id and isbn
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM favouritebook WHERE username=:username AND isbn=:isbn", nativeQuery = true)
    void removeBookFromFavourite(@Param("username")String username, @Param("isbn")String isbn);
}
