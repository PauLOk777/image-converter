package com.paulok777.exceptions;

import java.io.IOException;

public class InvalidDataException extends IOException {
    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
