package com.library.login.cotroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @GetMapping("testAPI")
    public ResponseEntity<String> testAPI(){
        return new ResponseEntity<>("{\"message\":\"Hello World!\"}", HttpStatus.OK);
    }
}
