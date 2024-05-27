package com.library.mongodb.exception;

import java.util.NoSuchElementException;

public class NoBookFound extends NoSuchElementException {
    public NoBookFound(String s) {
        super(s);
    }
}
