package com.lincentpega;

public class InvalidHandException extends RuntimeException {
    public InvalidHandException() {
    }

    public InvalidHandException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHandException(String message) {
        super(message);
    }
}
