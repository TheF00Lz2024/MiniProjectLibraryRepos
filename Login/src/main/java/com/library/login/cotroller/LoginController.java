package com.library.login.cotroller;

import com.library.login.exception.DuplicateUserId;
import com.library.login.exception.NoUserFound;
import com.library.login.model.User;
import com.library.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.common.hash.Hashing;


import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("testAPI")
    public ResponseEntity<String> testAPI(){
        return new ResponseEntity<>("{\"message\":\"Hello World!\"}", HttpStatus.OK);
    }

    // API to create new User
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        //hash user password
        String hashedPassword = Hashing.sha256()
                .hashString(user.getPassword(), StandardCharsets.UTF_8)
                .toString();
        user.setPassword(hashedPassword);
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    //API to get User
    @GetMapping("/user")
    public ResponseEntity<User> loginUser(@RequestParam("username") String username, @RequestParam("password") String password){
        //hash user password
        String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        return new ResponseEntity<>(userService.getUser(username, hashedPassword), HttpStatus.FOUND);
    }

    @ExceptionHandler(NoUserFound.class)
    public ResponseEntity<String> noUserFound(NoUserFound message){
        return new ResponseEntity<>(message.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateUserId.class)
    public ResponseEntity<String> duplicateUser(DuplicateUserId message){
        return new ResponseEntity<>(message.getMessage(), HttpStatus.FORBIDDEN);
    }
}
