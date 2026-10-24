package com.hacktheweb.tasktrack.exception;

import java.time.LocalDateTime;
import java.util.Map;

/** JSON body returned for every error. {@code details} maps field names to messages. */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> details) {
}
