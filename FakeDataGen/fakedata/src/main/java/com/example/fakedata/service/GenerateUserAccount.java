package com.example.fakedata.service;

import com.example.fakedata.configuration.FakeDataConfiguration;
import com.example.fakedata.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GenerateUserAccount {

    public void createUser() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(FakeDataConfiguration.class);
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 20000; i++) {
            User newUser = applicationContext.getBean(User.class);
            newUser.setUsername("NewAuthor"+i+"@gmail.com");
            newUser.setPassword("I@NewAuthor"+i);
            newUser.setRoles("Author");
            ResponseEntity<User> result = restTemplate.postForEntity("http://localhost:8081/api/v1/user", newUser, User.class);
            System.out.println("Fake data for Author");
            System.out.println(result);
        }

        for (int i = 0; i < 20000; i++) {
            User newUser = applicationContext.getBean(User.class);
            newUser.setUsername("NewUser"+i+"@gmail.com");
            newUser.setPassword("I@NewUser"+i);
            newUser.setRoles("User");
            ResponseEntity<User> result = restTemplate.postForEntity("http://localhost:8081/api/v1/user", newUser, User.class);
            System.out.println("Fake data for user");
            System.out.println(result);
        }
    }
}
