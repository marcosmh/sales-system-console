package com.mark.microsystem.sales.system.main.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String message
) { }
