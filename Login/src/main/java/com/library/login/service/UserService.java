package com.library.login.service;

import com.library.login.model.User;

public interface UserService {
    User createUser(User user);
    User getUser(String username, String password);
    User deleteUser(User user);
}
