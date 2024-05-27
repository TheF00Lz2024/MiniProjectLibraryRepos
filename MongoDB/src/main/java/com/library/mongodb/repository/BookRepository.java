package com.library.mongodb.repository;

import com.library.mongodb.model.Book;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
    @NonNull
    List<Book> findAll();

    @Transactional
    @Modifying
    @Query(value = "UPDATE book SET title=:title WHERE isbn=:isbn", nativeQuery = true)
    void updateBookDetail(@Param("title")String title, @Param("isbn")String isbn);
}
