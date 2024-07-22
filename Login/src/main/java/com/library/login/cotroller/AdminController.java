package com.library.login.cotroller;

import com.library.login.exception.ForbiddenAction;
import com.library.login.exception.NoUserFound;
import com.library.login.model.User;
import com.library.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    //API for removing user
    @DeleteMapping("/admin")
    public ResponseEntity<User> deleteUser(
            @RequestParam("username") String username) {
        return new ResponseEntity<>(userService.deleteUser(username), HttpStatus.OK);
    }

    @ExceptionHandler(NoUserFound.class)
    public ResponseEntity<String> noUserFound(NoUserFound message) {
        return new ResponseEntity<>(message.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenAction.class)
    public ResponseEntity<String> unableDeleteUser(ForbiddenAction message) {
        return new ResponseEntity<>(message.getMessage(), HttpStatus.FORBIDDEN);
    }
}
