package com.assignment.expensemanager.utils;

public class DataNotFoundException extends RuntimeException{
    String message;

    public DataNotFoundException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
