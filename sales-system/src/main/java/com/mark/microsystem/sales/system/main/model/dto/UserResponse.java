package com.mark.microsystem.sales.system.main.model.dto;

import java.time.LocalDateTime;

public record UserResponse(

        Integer id,

        String name,

        String username,

        String role,

        Boolean active,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) { }
