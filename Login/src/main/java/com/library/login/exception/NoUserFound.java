package com.library.login.exception;

import java.util.NoSuchElementException;

public class NoUserFound extends NoSuchElementException {
    public NoUserFound(String message) {
        super(message);
    }
}
