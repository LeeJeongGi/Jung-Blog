package com.junglog.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class JunglogException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public JunglogException(String message) {
        super(message);
    }

    public JunglogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
