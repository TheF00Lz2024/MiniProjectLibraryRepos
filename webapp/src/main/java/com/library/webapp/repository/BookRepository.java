package com.library.webapp.repository;

import com.library.webapp.model.Book;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends CrudRepository<Book, String> {
    @NonNull
    List<Book> findAll();

    @Query(value = "SELECT * FROM book WHERE favourite_book_set=:bookListId", nativeQuery = true)
    List<Book> findUserFavoriteBook(@Param("bookListId")int id);
}
