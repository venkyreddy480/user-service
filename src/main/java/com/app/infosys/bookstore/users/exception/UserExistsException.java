package com.app.infosys.bookstore.users.exception;

public class UserExistsException extends RuntimeException{

    public UserExistsException(String exception) {
        super(exception);
    }
}
