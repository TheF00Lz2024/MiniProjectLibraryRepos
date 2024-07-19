package com.library.login.cotroller;

import com.google.common.hash.Hashing;
import com.library.login.config.JWTTokenConfiguration;
import com.library.login.exception.DuplicateUserId;
import com.library.login.exception.ForbiddenAction;
import com.library.login.exception.NoUserFound;
import com.library.login.model.User;
import com.library.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final UserService userService;
    private final JWTTokenConfiguration jwtTokenConfiguration;

    @Autowired
    public LoginController(UserService userService, JWTTokenConfiguration jwtTokenConfiguration) {
        this.userService = userService;
        this.jwtTokenConfiguration = jwtTokenConfiguration;
    }

    @GetMapping("testAPI")
    public ResponseEntity<String> testAPI() {
        return new ResponseEntity<>("{\"message\":\"Hello World!\"}", HttpStatus.OK);
    }

    // API to create new User
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        //hash user password
        String hashedPassword = Hashing.sha256()
                .hashString(user.getPassword(), StandardCharsets.UTF_8)
                .toString();
        user.setPassword(hashedPassword);
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    //API to get User for login
    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> loginUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        //hash user password
        String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        User getuser = userService.getUser(username, hashedPassword);
        return new ResponseEntity<>(jwtTokenConfiguration.generateToken(getuser), HttpStatus.OK);
    }

    @GetMapping("/all-user")
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    //API for removing user
    @DeleteMapping("/user")
    public ResponseEntity<User> deleteUser(
            @RequestParam("username") String username,
            @RequestParam("userRoles") String userRoles,
            @RequestParam("deleteRoles") String deleteRoles) {
        return new ResponseEntity<>(userService.deleteUser(username, userRoles, deleteRoles), HttpStatus.OK);
    }

    @ExceptionHandler(NoUserFound.class)
    public ResponseEntity<String> noUserFound(NoUserFound message) {
        return new ResponseEntity<>(message.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateUserId.class)
    public ResponseEntity<String> duplicateUser(DuplicateUserId message) {
        return new ResponseEntity<>(message.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ForbiddenAction.class)
    public ResponseEntity<String> unableDeleteUser(ForbiddenAction message) {
        return new ResponseEntity<>(message.getMessage(), HttpStatus.FORBIDDEN);
    }
}
