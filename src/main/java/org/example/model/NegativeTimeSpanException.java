package org.example.model;

public class NegativeTimeSpanException extends RuntimeException {
    public NegativeTimeSpanException(String message) {
        super(message);
    }
}
