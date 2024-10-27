package org.lang.exceptions;

public class ExceptionOnCodeAnalysis  extends Exception {
    public ExceptionOnCodeAnalysis(String message) {
        super(message);
    }

    public ExceptionOnCodeAnalysis(String message, Throwable cause) {
        super(message, cause);
    }
}
