package com.example.fakedata.service;

import com.example.fakedata.configuration.FakeDataConfiguration;
import com.example.fakedata.model.Book;
import com.example.fakedata.model.UserLogin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class GenerateBook {

    private ResponseEntity<UserLogin> userLogin(){
        RestTemplate restTemplate =  new RestTemplate();
        return restTemplate.exchange(
                "http://localhost:8081//api/v1/user?username=NewAuthor0@gmail.com&password=I@NewAuthor0",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                UserLogin.class);
    }

    public void generateBook(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(FakeDataConfiguration.class);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+ Objects.requireNonNull(userLogin().getBody()).getToken());
        for (int i = 0; i < 40000; i++) {
            Book newBook = applicationContext.getBean(Book.class);
            newBook.setIsbn("NewBook"+i);
            newBook.setTitle("NewTitle"+i);
            newBook.setAuthorName("NewAuthor0");
            HttpEntity<Book> entity = new HttpEntity<>(newBook, headers);
            ResponseEntity<Book> result = restTemplate.postForEntity("http://localhost:8083/api/v1/author", entity, Book.class);

            System.out.println("Fake data for Book");
            System.out.println(result);
        }
    }
}
