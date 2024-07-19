package com.example.fakedata.configuration;

import com.example.fakedata.model.Book;
import com.example.fakedata.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakeDataConfiguration {

    @Bean
    public User user() {
        return new User();
    }

    @Bean
    public Book book() {
        return new Book();
    }
}
