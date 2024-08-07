package com.example.mongodb.repository;

import com.example.mongodb.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    @Query(value="{}", fields="{ 'imgData' : 0}")
    List<Book> findAll();
}
