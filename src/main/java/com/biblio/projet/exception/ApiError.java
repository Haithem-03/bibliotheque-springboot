package com.biblio.projet.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(LocalDateTime timestamp, int status, String message, Map<String, String> details) {
    public ApiError(int status, String message) {
        this(LocalDateTime.now(), status, message, null);
    }
}
