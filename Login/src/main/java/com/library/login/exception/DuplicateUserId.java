package com.library.login.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateUserId extends DuplicateKeyException {
    public DuplicateUserId(String msg) {
        super(msg);
    }
}
