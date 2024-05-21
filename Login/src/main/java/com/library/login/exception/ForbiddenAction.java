package com.library.login.exception;

public class ForbiddenAction extends UnsupportedOperationException {
    public ForbiddenAction(String message) {
        super(message);
    }
}
