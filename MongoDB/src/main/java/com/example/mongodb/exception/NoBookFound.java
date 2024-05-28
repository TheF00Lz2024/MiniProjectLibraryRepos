package com.example.mongodb.exception;

import java.util.NoSuchElementException;

public class NoBookFound extends NoSuchElementException {
    public NoBookFound(String message){
        super(message);
    }
}
