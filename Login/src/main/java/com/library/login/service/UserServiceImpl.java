package com.library.login.service;

import com.library.login.exception.DuplicateUserId;
import com.library.login.exception.ForbiddenAction;
import com.library.login.exception.NoUserFound;
import com.library.login.model.User;
import com.library.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if (userRepository.findById(user.getUsername()).isEmpty()) {
            return userRepository.save(user);
        } else {
            throw new DuplicateUserId("{\"message\":\"Username taken!\"}");
        }
    }

    @Override
    public User getUser(String username, String password) {
        List<User> foundUser = userRepository.loginUser(username, password);
        if (foundUser.isEmpty()) {
            throw new NoUserFound("{\"message\":\"Incorrect username or password!\"}");
        } else {
            return foundUser.getFirst();
        }
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.getAllUser();
    }

    @Override
    public User deleteUser(String username) {
        Optional<User> foundUser = userRepository.findById(username);
        if (foundUser.isEmpty()) {
            throw new NoUserFound("{\"message\":\"There is no such user account!\"}");
        } else {
            return checkUserRole(foundUser.get());
        }
    }

    public User checkUserRole(User user) {
        if (userRepository.checkUserRole(user.getUsername()).isEmpty()) {
            userRepository.deleteById(user.getUsername());
            return user;
        } else {
            throw new ForbiddenAction("{\"message\":\"Invalid Action!\"}");
        }
    }

//    @Override
//    public User deleteUser(String username, String userRoles, String deleteRoles) {
//        if (userRoles.equalsIgnoreCase("Admin")) {
//            if (deleteRoles.equalsIgnoreCase("User")) {
//                throw new ForbiddenAction("{\"message\":\"Cannot delete this User!\"}");
//            } else {
//                return deleteUserFunction(username, deleteRoles);
//            }
//        } else if (userRoles.equalsIgnoreCase("User")) {
//            if (deleteRoles.equalsIgnoreCase("User")) {
//                return deleteUserFunction(username, deleteRoles);
//            } else {
//                throw new ForbiddenAction("{\"message\":\"Forbidden delete of Account!\"}");
//            }
//        } else {
//            throw new ForbiddenAction("{\"message\":\"Forbidden delete of Account!\"}");
//        }
//
//    }
//
//    private User deleteUserFunction(String username, String roles) {
//        List<User> foundUser = userRepository.findEmployee(username, roles);
//        if (foundUser.isEmpty()) {
//            throw new NoUserFound("{\"message\":\"No such user exist!\"}");
//        } else {
//            userRepository.deleteById(username);
//            return foundUser.getFirst();
//        }
//    }

}
