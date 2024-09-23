package com.app.infosys.bookstore.users.dto;

import org.springframework.http.HttpStatus;

public class ErrorDTO {

    String message;
    HttpStatus status;

    public ErrorDTO(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
