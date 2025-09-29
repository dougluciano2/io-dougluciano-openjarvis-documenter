package io.dougluciano.ai.openjarvis.documenter.infrastructure.web.exceptionhandler;

import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
