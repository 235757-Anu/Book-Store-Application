package com.ust.book.exception;

public class BookAlreadyAddedException extends RuntimeException {
    public BookAlreadyAddedException(String message) {
        super(message);

    }
}