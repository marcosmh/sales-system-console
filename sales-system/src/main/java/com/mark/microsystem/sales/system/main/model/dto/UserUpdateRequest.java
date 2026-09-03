package com.mark.microsystem.sales.system.main.model.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Role is required")
        String role,

        Boolean active

) { }
