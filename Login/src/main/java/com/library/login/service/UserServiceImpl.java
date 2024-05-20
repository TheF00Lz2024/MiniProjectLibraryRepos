package com.library.login.service;

import com.library.login.exception.DuplicateUserId;
import com.library.login.exception.NoUserFound;
import com.library.login.model.User;
import com.library.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if(userRepository.findById(user.getUserId()).isEmpty()){
            return userRepository.save(user);
        }else{
            throw new DuplicateUserId("{\"message\":\"Username taken!\"}");
        }
    }

    @Override
    public User getUser(String username, String password) {
        List<User> foundUser = userRepository.loginUser(username, password);
        if(foundUser.isEmpty()){
            throw new NoUserFound("{\"message\":\"Incorrect username or password!\"}");
        }else{
            return foundUser.getFirst();
        }
    }

    @Override
    public User deleteUser(User user) {
        return null;
    }
}
