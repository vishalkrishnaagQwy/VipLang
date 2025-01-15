package org.lang.exceptions;

public class ExceptionOnDetailedAnalysis extends RuntimeException {
    public ExceptionOnDetailedAnalysis(ExceptionOnCodeAnalysis message) {
        super(message);
    }
}
