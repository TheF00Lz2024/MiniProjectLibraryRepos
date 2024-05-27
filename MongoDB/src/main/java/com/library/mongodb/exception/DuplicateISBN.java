package com.library.mongodb.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateISBN extends DuplicateKeyException {
    public DuplicateISBN(String msg) {
        super(msg);
    }
}
