package com.paulok777.exceptions;

public class WrongArgumentsException extends RuntimeException {
    public WrongArgumentsException() {
        super();
    }

    public WrongArgumentsException(String message) {
        super(message);
    }
}
