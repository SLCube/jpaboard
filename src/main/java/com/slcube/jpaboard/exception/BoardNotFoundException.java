package com.slcube.jpaboard.exception;

public class BoardNotFoundException extends IllegalArgumentException{
    public BoardNotFoundException() {
        super();
    }

    public BoardNotFoundException(String s) {
        super(s);
    }

    public BoardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardNotFoundException(Throwable cause) {
        super(cause);
    }
}
