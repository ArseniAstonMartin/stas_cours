package by.exam.prep.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponse(
        int status,
        String message,
        String path,
        LocalDateTime timestamp,
        Map<String, String> errors
) {
    public ApiErrorResponse(int status, String message, String path) {
        this(status, message, path, LocalDateTime.now(), null);
    }
}
