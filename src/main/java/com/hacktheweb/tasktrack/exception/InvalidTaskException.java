package com.hacktheweb.tasktrack.exception;

/** Thrown when a request is well-formed JSON but breaks a business rule. */
public class InvalidTaskException extends RuntimeException {

    private final String field;

    public InvalidTaskException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
