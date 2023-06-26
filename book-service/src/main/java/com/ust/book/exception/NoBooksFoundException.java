package com.ust.book.exception;

public class NoBooksFoundException extends RuntimeException{
    public NoBooksFoundException(String s) {
        super(s);
    }
}
