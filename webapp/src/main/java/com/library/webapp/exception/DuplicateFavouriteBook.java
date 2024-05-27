package com.library.webapp.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateFavouriteBook extends DuplicateKeyException {
    public DuplicateFavouriteBook(String msg) {
        super(msg);
    }
}
