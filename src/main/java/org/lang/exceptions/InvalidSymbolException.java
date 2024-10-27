package org.lang.exceptions;

public class InvalidSymbolException extends Exception {
    public InvalidSymbolException(String message) {
        super(message);
    }

    public InvalidSymbolException(String message, Throwable cause) {
        super(message, cause);
    }
}

