package com.example.fakedata.service;


import com.example.fakedata.model.UserLogin;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class LoginUser {

    public UserLogin userLogin(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<UserLogin> loginUser = restTemplate.
                exchange("http://localhost:8081/api/v1/user?username=" + username + "&password=" + password, HttpMethod.GET, entity, UserLogin.class);
        System.out.println(loginUser.getBody());
        return loginUser.getBody();
    }
}
