package com.library.login.config;

import com.library.login.model.User;

import java.util.Map;

public interface JWTTokenConfiguration {
    Map<String, String> generateToken(User user);
}
