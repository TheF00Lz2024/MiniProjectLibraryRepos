package com.library.login.service;

import com.library.login.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUser(String username, String password);

    List<User> getAllUser();

    User deleteUser(String username);
}
