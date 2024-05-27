package com.library.webapp.exception;

import java.util.NoSuchElementException;

public class NoFavouriteBookFound extends NoSuchElementException {
    public NoFavouriteBookFound(String s) {
        super(s);
    }
}
