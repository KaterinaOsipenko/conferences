package com.epam.conferences.exception;

public class NoElementsException extends Exception {

    public NoElementsException() {
    }

    public NoElementsException(String message) {
        super(message);
    }

    public NoElementsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoElementsException(Throwable cause) {
        super(cause);
    }

    public NoElementsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
